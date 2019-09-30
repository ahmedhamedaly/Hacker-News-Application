
public class FrequentWebsites {
	private int frequency;
	private String url;
	
	FrequentWebsites(String url, int frequency){
		this.url = url;
		this.frequency = frequency;
	}
	public void setFrequency(int x) {
		frequency = x;
	}
	public int returnFrequency() {
		return frequency;
	}
	public void setUrl(String y) {
		url = y;
	}
	public String returnURL() {
		return url;
	}
}
