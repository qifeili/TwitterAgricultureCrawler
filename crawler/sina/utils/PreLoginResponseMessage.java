package crawler.sina.utils;

public class PreLoginResponseMessage {
	private Integer retcode;
	private Long servertime;
	private String pcid;
	private String nonce;
	private Integer exectime;
	private String pubkey;
	private String rsakv;
	private Integer showpin;

	public Integer getRetcode() {
		return retcode;
	}

	public void setRetcode(Integer retcode) {
		this.retcode = retcode;
	}

	public Long getServertime() {
		return servertime;
	}

	public void setServertime(Long servertime) {
		this.servertime = servertime;
	}

	public String getPcid() {
		return pcid;
	}

	public void setPcid(String pcid) {
		this.pcid = pcid;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public Integer getExectime() {
		return exectime;
	}

	public void setExectime(Integer exectime) {
		this.exectime = exectime;
	}

	public String getRsakv() {
		return rsakv;
	}

	public void setRsakv(String rsakv) {
		this.rsakv = rsakv;
	}

	public String getPubkey() {
		return pubkey;
	}

	public void setPubkey(String pubkey) {
		this.pubkey = pubkey;
	}

	public Integer getShowpin() {
		return showpin;
	}

	public void setShowpin(Integer showpin) {
		this.showpin = showpin;
	}

}
