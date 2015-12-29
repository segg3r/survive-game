package by.segg3r.upgradeclient;

public enum UpgradeResult {

	UPGRADER_UPGRADED(1), CLIENT_UPGRADED(2), NO_UPGRADE(0), UPGRADE_FAILED(3);
	
	private int resultCode;
	
	UpgradeResult(int resultCode) {
		this.resultCode = resultCode;
	}
	
	public int getResultCode() {
		return this.resultCode;
	}
	
}
