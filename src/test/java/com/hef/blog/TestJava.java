
package com.hef.blog;

// Java program to demonstrate multiple inheritance
// in interfaces
import java.io.*;
/* File name : Employee.java */



/**
 * Why Multiple Inheritance is not supported through a class
 * in Java, but it can be possible through the interface?
 *
 * Multiple Inheritance is not supported by class because
 * of ambiguity. In case of interface, there is no ambiguity
 * because implementation to the method(s) is provided by
 * the implementing class up to Java 7. From Java 8, interfaces
 * also have implementations of methods. So if a class
 * implementing two or more interfaces having the same method
 * signature with implementation, it is mandated to implement
 * the method in class also.
 *
 *
 * */