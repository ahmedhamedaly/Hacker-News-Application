
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import processing.core.*;

public class AutoComplete extends PApplet {

	PApplet parent;

	float x;
	float y;
	float w;
	float h;

	private String inputWord = "";

	ArrayList<String> dictionary = new ArrayList<>();
	ArrayList<Integer> index = new ArrayList<>();

	public AutoComplete(PApplet parent, float x, float y, float w, float h) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

		try {
			readDictionary();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void readDictionary() throws IOException {
		String currentWord;

		try {
			FileReader wordRead = new FileReader("src/words.txt");
			BufferedReader readWords = new BufferedReader(wordRead);
			boolean endOfFile = false;
			while (!endOfFile) {
				currentWord = readWords.readLine();
				if (currentWord != null) {
					dictionary.add(currentWord);
				} else
					endOfFile = true;
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}

	}

	public void newEntry(String inputWord) {
		this.inputWord = inputWord;
	}

	public void draw() {
		String autoFill = "";
		if (inputWord != "") {
			for (String temp : dictionary) {
				if (temp.startsWith(inputWord)) {
					parent.fill(0);
//					if (Raaddit.searchEngine.active == false)
//						parent.text("", x + 10, y + 30);
					parent.text(temp, x + 10, y + 30);
					autoFill = temp;
					break;
				}
			}
			
			if (parent.key == TAB && Raaddit.searchBar.searchEngineActive()) {
				Raaddit.searchBar.sendInput(autoFill);
				Raaddit.currentScreen = 0;
				Raaddit.searchBar.setSearchActive();
				Raaddit.search = "";
			}
		}
		//parent.text(Raaddit.search, x, y + 30);

	}
	
}
