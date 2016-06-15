package by.segg3r.git;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.maven.cli.MavenCli;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.springframework.beans.factory.annotation.Autowired;

import by.segg3r.Application;
import by.segg3r.FileSystem;

public class GitRepository {

	@Autowired
	private MavenCli mavenCli;

	private static final String DEPLOY_DIRECTORY = "deploy";
	private static final String GIT_HIDDEN_DIRECTORY_NAME = ".git";

	private String remoteUrl;
	private String relativeDirectoryPath;
	private String directoryPath;
	private String gitPath;

	private Git repository;

	public GitRepository(String remoteUrl, String relativeDirectoryPath) {
		super();
		this.remoteUrl = remoteUrl;
		this.relativeDirectoryPath = relativeDirectoryPath;
		this.directoryPath = FileSystem.getRelativePath(relativeDirectoryPath);
		this.gitPath = FileSystem.getRelativePath(relativeDirectoryPath
				+ FileSystem.FILE_SPLITTER + GIT_HIDDEN_DIRECTORY_NAME);
	}

	public void initialize() throws GitRepositoryException {
		this.repository = exists() ? open() : create();
	}

	public boolean exists() {
		File hiddenGitDirectory = getHiddenGitDirectory();
		return hiddenGitDirectory.exists();
	}

	public Git create() throws GitRepositoryException {
		try {
			return Git.cloneRepository().setURI(remoteUrl)
					.setDirectory(getDirectory()).setCloneAllBranches(true)
					.call();
		} catch (Exception e) {
			throw new GitRepositoryException(
					"Could not clone git repository from remote url "
							+ remoteUrl + " into directory " + directoryPath, e);
		}
	}

	public Git open() throws GitRepositoryException {
		try {
			Git git = Git.open(getDirectory());
			git.fetch().call();

			return git;
		} catch (Exception e) {
			throw new GitRepositoryException(
					"Could not open git repository at location "
							+ directoryPath, e);
		}
	}

	public List<String> getVersions() throws GitRepositoryException {
		try {
			List<Ref> refs = this.repository.tagList().call();
			return refs.stream().map(Ref::getName).collect(Collectors.toList());
		} catch (Exception e) {
			throw new GitRepositoryException(
					"Could not get list of tags from repository "
							+ directoryPath, e);
		}
	}

	public void buildVersion(String buildVersion) throws GitRepositoryException {
		checkout(buildVersion);
		executeMavenBuild();
	}

	private void checkout(String buildVersion) throws GitRepositoryException {
		try {
			this.repository.checkout().setName(buildVersion).call();
		} catch (GitAPIException e) {
			throw new GitRepositoryException("Could not checkout tag "
					+ buildVersion + " at repository " + directoryPath, e);
		}
	}

	private void executeMavenBuild() throws GitRepositoryException {
		int result = mavenCli.doMain(new String[] { "clean", "install" },
				directoryPath, System.out, System.out);
		if (result != 0) {
			throw new GitRepositoryException(
					"Could not execute maven build in " + directoryPath);
		}
	}
	
	public String getDeployDirectory(Application application) {
		return FileSystem.getRelativePath(relativeDirectoryPath
				+ FileSystem.FILE_SPLITTER + DEPLOY_DIRECTORY
				+ FileSystem.FILE_SPLITTER + application.getPath());
	}

	public void close() {
		if (this.repository != null) {
			this.repository.close();
		}
	}

	private File getDirectory() {
		return new File(directoryPath);
	}

	private File getHiddenGitDirectory() {
		return new File(gitPath);
	}

}
