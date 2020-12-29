package com.example.jna;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.sun.jna.FunctionMapper;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

public class AddBasicTest {

	private static AddBasicLibrary INSTANCE;
	
	static {
		Map<String, FunctionMapper> map = new HashMap<String, FunctionMapper>();
		Map<String, String> javaNameCNameMap = new HashMap<String, String>();
		javaNameCNameMap.put("add", "Add");
		map.put(Library.OPTION_FUNCTION_MAPPER, new FunctionMapper() {
			@Override
			public String getFunctionName(NativeLibrary library, Method method) {
				return javaNameCNameMap.get(method.getName());
			}
		});
		INSTANCE = Native.load("add_basic", AddBasicLibrary.class, map);
	}
	
	public interface AddBasicLibrary extends Library {
		int add(int a, int b);
	}
	
	public static void main(String[] args) {
		System.out.println(INSTANCE.add(100, 2));
	}

}
