package by.segg3r.git;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import org.eclipse.jgit.api.Git;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.segg3r.Application;

public class GitRepositoryTest {

	private GitRepository repository;
	
	@BeforeClass
	public void initMocks() {
		GitRepository rawRepository = new GitRepository("remoteUrl", "repo");
		this.repository = spy(rawRepository);
	}
	
	@AfterMethod
	public void resetMocks() {
		reset(repository);
	}
	
	@Test(description = "should create repository if not exists")
	public void testInitializeCreate() throws GitRepositoryException {
		doReturn(false).when(repository).exists();
		
		Git createGit = mock(Git.class);
		doReturn(createGit).when(repository).create();
		Git openGit = mock(Git.class);
		doReturn(openGit).when(repository).open();
		
		repository.initialize();
		
		verify(repository).create();
	}
	
	@Test(description = "should create repository if exists")
	public void testInitializeOpen() throws GitRepositoryException {
		doReturn(true).when(repository).exists();
		
		Git createGit = mock(Git.class);
		doReturn(createGit).when(repository).create();
		Git openGit = mock(Git.class);
		doReturn(openGit).when(repository).open();
		
		repository.initialize();
		
		verify(repository).open();
	}
	
	@Test(description = "should correctly build repository path")
	public void testGetDeployPath() {
		System.setProperty("user.dir", "D:/upgrade-server");
		
		assertEquals(repository.getDeployDirectory(Application.CLIENT),
				"D:/upgrade-server/repo/deploy/client");
	}
	
}
