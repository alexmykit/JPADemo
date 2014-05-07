package com.jpa.utilities;

public interface Projector<S, D>
{
	D project(S item);
}
