import java.sql.Connection;
import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collections;

import processing.core.PApplet;
import processing.core.PGraphics;

public class WebsiteCompositionPieChart {
	PApplet parent;
	ArrayList<String> websites;
	FrequentWebsites[] frequentWebsites = new FrequentWebsites[10];
	float diameter = 500;
	float prevAngle = 0;
	int[] colors;
	float[] angles = new float[10];
	final float MAX_ANGLE = 360;
	int xOffset = 20;
	int yOffset = 8;
	int margin = 50;
	int everythingShown;

	public WebsiteCompositionPieChart(PApplet parent) {
		this.parent = parent;
		everythingShown = 0;
	}

	public void updateData() {
		everythingShown=0;
		websites = Raaddit.query.websiteComposition();
		countWebsites();
		websites = null;
		pieChartAngleCreation();
		colors = new int[angles.length];

		diameter = (float) (PApplet.min(parent.width, parent.height) * 0.75);
		for (int i = 0; i < angles.length; i++) {
			int r = (int) parent.random(255);
			int g = (int) parent.random(255);
			int b = (int) parent.random(255);

			if (r > 190)
				r -= 75;
			if (r < 65)
				r += 75;

			if (g > 190)
				g -= 75;
			if (g < 65)
				g += 75;

			if (b > 190)
				b -= 75;
			if (b < 65)
				b += 75;
			colors[i] = parent.color(r, g, b);
		}

	}
	
	public void update(){
		frequentWebsites = Raaddit.query.websitesCount();
		pieChartAngleCreation();
		colors = new int[angles.length];

		diameter = (float) (PApplet.min(parent.width, parent.height) * 0.75);
		for (int i = 0; i < angles.length; i++) {
			int r = (int) parent.random(255);
			int g = (int) parent.random(255);
			int b = (int) parent.random(255);

			if (r > 190)
				r -= 75;
			if (r < 65)
				r += 75;

			if (g > 190)
				g -= 75;
			if (g < 65)
				g += 75;

			if (b > 190)
				b -= 75;
			if (b < 65)
				b += 75;
			colors[i] = parent.color(r, g, b);
		}
	}

	public void countWebsites() {
		ArrayList<FrequentWebsites> tempList = new ArrayList<FrequentWebsites>();
		for (int index = 0; index < websites.size(); index++) {
			FrequentWebsites temp1 = new FrequentWebsites(websites.get(index), Collections.frequency(websites, websites.get(index)));
			int frequency = 0;
			for (FrequentWebsites freq : tempList)
				if (temp1.returnURL().equals(freq.returnURL()))
					frequency++;
			if (frequency == 0)
				tempList.add(temp1);
		}
		int swaps = 0;
		do {
			swaps = 0;
			for (int i = 1; i < tempList.size(); i++) {
				if (tempList.get(i).returnFrequency() > tempList.get(i - 1).returnFrequency()) {
					FrequentWebsites temp = tempList.get(i - 1);
					tempList.set(i - 1, tempList.get(i));
					tempList.set(i, temp);
					swaps++;
				}
			}
		} while (swaps > 0);
//		ArrayList<Integer> tempIntList = new ArrayList<>();
//		for (int index = 0;index<tempList.size();index++) {
//			tempIntList.add(tempList.get(index).returnFrequency());			
//		}
//		Collections.sort(tempIntList);
		ArrayList<FrequentWebsites> top10Values = new ArrayList<FrequentWebsites>(tempList.subList(0, 10));
//		for (int index = 0;index <top10Values.size();index++) {
//			for(int j=0;j<tempList.size();j++) {
//				if (top10Values.get(index)==tempList.get(j).returnFrequency()) {
//					frequentWebsites[index].setFrequency(tempList.get(j).returnFrequency());
//					frequentWebsites[index].setUrl(tempList.get(j).returnURL());
//				}
//			}
//		}
		frequentWebsites = top10Values.toArray(new FrequentWebsites[0]);
	}

	public void pieChartAngleCreation() {
		int tempTotal = 0;
		for (int index = 0; index < frequentWebsites.length; index++) {
			tempTotal += frequentWebsites[index].returnFrequency();
		}
		for (int index = 0; index < frequentWebsites.length; index++) {
			angles[index] = ((float) frequentWebsites[index].returnFrequency() / (float) tempTotal) * 360;
		}
	}

	void pieChart() {
		parent.background(255);
		float lastAngle = 0;
		for (int i = 0; i < angles.length; i++) {
			parent.fill(colors[i]);
			parent.arc(1920 / 2, 1080 / 2, diameter, diameter, lastAngle, lastAngle + PApplet.radians(angles[i]));
			lastAngle += PApplet.radians(angles[i]);
		}
		parent.fill(0);
		parent.textSize(35);
		parent.text("Website Composition of latest 1000 websites", 1000, 1080 - 50);
		for (int i = 0; i < angles.length; i++) {
			switch (i) {
			case 1:
				parent.fill(colors[i]);
				parent.rect(50, 3 * parent.height / 4 + i * 40, 20, 20);
				parent.fill(127);
				parent.textAlign(PApplet.LEFT);
				parent.textSize(14);
				parent.text("URL = " + frequentWebsites[i].returnURL() + " : " + frequentWebsites[i].returnFrequency(),
						60 + 20, 3 * parent.height / 4 + 15 + 40 * i);
				break;
			case 2:
				parent.fill(colors[i]);
				parent.rect(50, 3 * parent.height / 4 + i * 40, 20, 20);
				parent.fill(127);
				parent.textAlign(PApplet.LEFT);
				parent.textSize(14);
				parent.text("URL = " + frequentWebsites[i].returnURL() + " : " + frequentWebsites[i].returnFrequency(),
						60 + 20, 3 * parent.height / 4 + 15 + 40 * i);
				break;
			case 3:
				parent.fill(colors[i]);
				parent.rect(50, 3 * parent.height / 4 + i * 40, 20, 20);
				parent.fill(127);
				parent.textAlign(PApplet.LEFT);
				parent.textSize(14);
				parent.text("URL = " + frequentWebsites[i].returnURL() + " : " + frequentWebsites[i].returnFrequency(),
						60 + 20, 3 * parent.height / 4 + 15 + 40 * i);
				break;
			case 4:
				parent.fill(colors[i]);
				parent.rect(50, 3 * parent.height / 4 + i * 40, 20, 20);
				parent.fill(127);
				parent.textAlign(PApplet.LEFT);
				parent.textSize(14);
				parent.text("URL = " + frequentWebsites[i].returnURL() + " : " + frequentWebsites[i].returnFrequency(),
						60 + 20, 3 * parent.height / 4 + 15 + 40 * i);
				break;
			case 5:
				parent.fill(colors[i]);
				parent.rect(50, 3 * parent.height / 4 - (i - 4) * 40, 20, 20);
				parent.fill(127);
				parent.textAlign(PApplet.LEFT);
				parent.textSize(14);
				parent.text("URL = " + frequentWebsites[i].returnURL() + " : " + frequentWebsites[i].returnFrequency(),
						60 + 20, 3 * parent.height / 4 + 15 - 40 * (i - 4));
				break;
			case 6:
				parent.fill(colors[i]);
				parent.rect(50, 3 * parent.height / 4 - (i - 4) * 40, 20, 20);
				parent.fill(127);
				parent.textAlign(PApplet.LEFT);
				parent.textSize(14);
				parent.text("URL = " + frequentWebsites[i].returnURL() + " : " + frequentWebsites[i].returnFrequency(),
						60 + 20, 3 * parent.height / 4 + 15 - 40 * (i - 4));
				break;
			case 7:
				parent.fill(colors[i]);
				parent.rect(50, 3 * parent.height / 4 - (i - 4) * 40, 20, 20);
				parent.fill(127);
				parent.textAlign(PApplet.LEFT);
				parent.textSize(14);
				parent.text("URL = " + frequentWebsites[i].returnURL() + " : " + frequentWebsites[i].returnFrequency(),
						60 + 20, 3 * parent.height / 4 + 15 - 40 * (i - 4));
				break;
			case 8:
				parent.fill(colors[i]);
				parent.rect(50, 3 * parent.height / 4 - (i - 4) * 40, 20, 20);
				parent.fill(127);
				parent.textAlign(PApplet.LEFT);
				parent.textSize(14);
				parent.text("URL = " + frequentWebsites[i].returnURL() + " : " + frequentWebsites[i].returnFrequency(),
						60 + 20, 3 * parent.height / 4 + 15 - 40 * (i - 4));
				break;
			case 9:
				parent.fill(colors[i]);
				parent.rect(50, 3 * parent.height / 4 - (i - 4) * 40, 20, 20);
				parent.fill(127);
				parent.textAlign(PApplet.LEFT);
				parent.textSize(14);
				parent.text("URL = " + frequentWebsites[i].returnURL() + " : " + frequentWebsites[i].returnFrequency(),
						60 + 20, 3 * parent.height / 4 + 15 - 40 * (i - 4));
				break;
			default:
				parent.fill(colors[i]);
				parent.rect(50, 3 * parent.height / 4 + i * 40, 20, 20);
				parent.fill(127);
				parent.textAlign(PApplet.LEFT);
				parent.textSize(14);
				parent.text("URL = " + frequentWebsites[i].returnURL() + " : " + frequentWebsites[i].returnFrequency(),
						60 + 20, 3 * parent.height / 4 + 15 + 40 * i);
				break;
			}
		}
		mouseOver();
	}

	public void mouseOver() {
		if(PApplet.dist(parent.mouseX, parent.mouseY, 1920/2, 1080/2) < diameter/2)
			for(int i = 0; i < colors.length; i++)
				if(isMouseOver(colors[i]))
				{
//					parent.stroke(255);
//					parent.fill(0, 0, 0, 100);
//					parent.rect(parent.mouseX, parent.mouseY - 50, 150 + ((frequentWebsites[i].returnURL().length()>21)?(frequentWebsites[i].returnURL().length() - 21)*12:0), 50, 5);
					parent.fill(255);
					parent.text(frequentWebsites[i].returnURL(), parent.mouseX + 10, parent.mouseY - 35);
					parent.text("Frequency: " + frequentWebsites[i].returnFrequency(), parent.mouseX + 10, parent.mouseY - 20);
				}
			
	}
	
	public boolean isMouseOver(int colorSector) {
		if (parent.get(parent.mouseX, parent.mouseY) == colorSector)
			return true;
		return false;
	}

}
