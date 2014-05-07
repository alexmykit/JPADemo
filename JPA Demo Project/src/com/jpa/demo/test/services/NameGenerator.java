package com.jpa.demo.test.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NameGenerator
{
	public NameGenerator(String[] words, String separator, boolean allowDuplicates, int nWordsToTake)
	{
		if (words.length == 0)
			throw new IllegalArgumentException("Empty words array passed");
		
		this.words = words;
		this.allowDuplicates = allowDuplicates;
		this.separator = separator;
		this.nWordsToTake = nWordsToTake > 0 && nWordsToTake <= words.length ? nWordsToTake : (words.length >= 2 ? 2 : words.length);
	}
	
	public String nextRandomName()
	{
		List<String> wordsList = new ArrayList<>(Arrays.asList(words));
		int nWordsToTake = this.nWordsToTake;
		
		String name = "";
		
		while(nWordsToTake-- != 0)
		{
			name += wordsList.remove(RAND.nextInt(wordsList.size())) + (nWordsToTake == 0 ? "" : separator);
		}
		
		if (!allowDuplicates)
			name += counter++;
		
		return name;
	}
	
	private final String[] words;
	private final int nWordsToTake;
	private final String separator;
	
	private boolean allowDuplicates;
	private static final Random RAND = new Random(System.currentTimeMillis());
	
	private static int counter;
}
