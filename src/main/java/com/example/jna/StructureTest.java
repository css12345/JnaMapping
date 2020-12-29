package com.example.jna;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.sun.jna.FunctionMapper;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Structure;

public class StructureTest {
	
	private static StructureLibrary INSTANCE;
	
	static {
		Map<String, FunctionMapper> map = new HashMap<String, FunctionMapper>();
		Map<String, String> javaNameCNameMap = new HashMap<String, String>();
		javaNameCNameMap.put("radixConversion", "RadixConversion");
		map.put(Library.OPTION_FUNCTION_MAPPER, new FunctionMapper() {
			@Override
			public String getFunctionName(NativeLibrary library, Method method) {
				return javaNameCNameMap.get(method.getName());
			}
		});
		INSTANCE = Native.load("test_structure", StructureLibrary.class, map);
	}
	
	@Structure.FieldOrder({ "elem", "top" })
	public static class SeqStack extends Structure {
		public static class ByValue extends SeqStack implements Structure.ByValue {
		}

		public static class ByReference extends SeqStack implements Structure.ByReference {
		}

		public byte[] elem;
		public int top;
	}

	public interface StructureLibrary extends Library {
		void radixConversion(int element, int radix, SeqStack seqStack, byte[] result);
	}

	public static void main(String[] args) {
		SeqStack seqStack = new SeqStack();
		seqStack.top = -1;
		seqStack.elem = new byte[20];
		byte[] result = new byte[20];
		INSTANCE.radixConversion(155555, 4, seqStack, result);
		System.out.println(Native.toString(result));
	}
}
