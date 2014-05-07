package com.jpa.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectionUtils
{
	public static <S,D> List<D> projectToList(Iterable<S> sourceValues, Projector<S, D> projector)
	{
		List<D> result = new ArrayList<>();
		
		if (sourceValues == null)
			return result;
		
		for (S value : sourceValues)
		{
			result.add(projector.project(value));
		}
			
		return result;
	}
	
	public static String[] range(char start, char end)
	{
		if (start >= end)
			throw new IllegalArgumentException("Start >= end");
		
		String[] charsRange = new String[end - start + 1];
		
		int idx = 0;
		for (char value = start; value <= end; value++)
		{
			charsRange[idx++] = value + "";
		}
		
		return charsRange;
	}
}