package by.segg3r.upgradeclient;

public enum UpgradeResult {

	UPGRADER_UPGRADED(2), CLIENT_UPGRADED(3), NO_UPGRADE(0);
	
	private int resultCode;
	
	UpgradeResult(int resultCode) {
		this.resultCode = resultCode;
	}
	
	public int getResultCode() {
		return this.resultCode;
	}
	
}
