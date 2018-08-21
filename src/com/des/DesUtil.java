package com.des;

import javax.crypto.Cipher;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

public class DesUtil {
	public static byte[] desEncrypt(byte[] orig, byte[] key) throws Exception {
		Cipher desCipher = Cipher.getInstance("DES/ECB/NoPadding");
		DESKeySpec deskey = new DESKeySpec(key);
		SecretKeySpec keySpec = new SecretKeySpec(deskey.getKey(), "DES");
		desCipher.init(Cipher.ENCRYPT_MODE, keySpec);
		return desCipher.doFinal(orig);
	}

	public static byte[] desDecrypt(byte[] des, byte[] key) throws Exception {
		Cipher desCipher = Cipher.getInstance("DES/ECB/NoPadding");
		DESKeySpec deskey = new DESKeySpec(key);
		SecretKeySpec keySpec = new SecretKeySpec(deskey.getKey(), "DES");
		desCipher.init(Cipher.DECRYPT_MODE, keySpec);
		return desCipher.doFinal(des);
	}

	public static byte[] desDecrypt2(byte[] des, byte[] key) throws Exception {
		byte[] K1 = new byte[key.length / 2];
		byte[] K2 = new byte[key.length / 2];
		System.arraycopy(key, 0, K1, 0, key.length / 2);
		System.arraycopy(key, key.length / 2, K2, 0, key.length / 2);
		byte[] ret = desDecrypt(des, K1);
		ret = desEncrypt(ret, K2);
		ret = desDecrypt(ret, K1);
		return ret;
	}
	public static byte[] desEncrypt2(byte[] des, byte[] key) throws Exception {
		byte[] K1 = new byte[key.length / 2];
		byte[] K2 = new byte[key.length / 2];
		System.arraycopy(key, 0, K1, 0, key.length / 2);
		System.arraycopy(key, key.length / 2, K2, 0, key.length / 2);
		byte[] ret = desEncrypt(des, K1);
		ret = desDecrypt(ret, K2);
		ret = desEncrypt(ret, K1);
		return ret;
	}
	
    public static String decryptPin(String pin,String keyPin,String pan) throws Exception{
		if(pan!=null){
			byte[] key = desDecrypt2(hexStringToByte(pin),  hexStringToByte(keyPin));
			String tmp_bin = "0000"+ pan.substring(pan.length()-13);
			key = xor(key,hexStringToByte(tmp_bin.substring(0,16)));
			String keyString =  byte2HexString(key);
			int keyLen = Integer.parseInt(keyString.substring(0, 2));
			String keyBlock =  keyString.substring(2, 2+keyLen);
			System.out.println(keyString);
			return keyBlock;
		}else{
			byte[] key = desDecrypt(hexStringToByte(pin),  hexStringToByte(keyPin));
			String keyString =  byte2HexString(key);
			int keyLen = Integer.parseInt(keyString.substring(0, 2));
			String keyBlock =  keyString.substring(2, 2+keyLen);
			System.out.println(keyString);
			return keyBlock;
		}
		
	}
    
    public static byte[] encryptPin(String pin,String keyPin,String pan,String f53) throws Exception{
    	
//    	byte[] pinByte= new byte[8];
//    	pinByte[0]=(byte)0x06;
//    	pinByte[1]=(byte)0x99;
//    	pinByte[2]=(byte)0x99;
//    	pinByte[3]=(byte)0x99;
//    	pinByte[4]=(byte)0xFF;
//    	pinByte[5]=(byte)0xFF;
//    	pinByte[6]=(byte)0xFF;
//    	pinByte[7]=(byte)0xFF;
//		System.out.println(byte2HexString(pinByte));
		String typePan = f53.substring(0,1);
		String pinBlockHex=autoFixedLen("L", '0', 2, pin.length()+"");
		pinBlockHex=pinBlockHex+pin;
		pinBlockHex = autoFixedLen("R", 'F', 16, pinBlockHex);
		System.out.println(pinBlockHex);
		byte[] pinByte = hexStringToByte(pinBlockHex);
		if("1".equals(typePan)){
			byte[] resultB=desEncrypt(pinByte,hexStringToByte(keyPin));
			System.out.println(byte2HexString(resultB));
			return resultB;
		}else if("2".equals(typePan)){
			String tmp_bin = "0000"+ pan.substring(pan.length()-13);
			byte[] blockPin = xor(pinByte,hexStringToByte(tmp_bin.substring(0,16)));
			byte[] key = desEncrypt2(blockPin,hexStringToByte(keyPin));
			String keyString =  byte2HexString(key);
			System.out.println(keyString);
			return key;
		}else{
			return null;
		}
	}
    
    
    /**CBC**/
	public static byte[] countMAC(byte[] bbb, int kkk,String macK) throws Exception {
		byte[] bb = {};
//		KeyStoreUtils keyStoreUtils=new KeyStoreUtils();
		if (kkk == 1) {
			bb = new byte[bbb.length];
			System.arraycopy(bbb, 0, bb, 0, bbb.length);
			bb[9] = (byte) (bb[9] + 1); // 濞屸剝婀�4閻ㄥ嫪缍呴崶锟�		} else {
			bb = new byte[bbb.length - 8];
			System.arraycopy(bbb, 0, bb, 0, bbb.length - 8);
		}
		byte[] ret = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
		int n = 8 - bb.length % 8;
		int part = bb.length / 8 + 1;
		if (n == 8) {
			n = 0;
			part--;
		}
		byte[] temp = new byte[bb.length + n];
		System.arraycopy(bb, 0, temp, 0, bb.length);
		byte[] desTemp = new byte[8];
//		String macKeyString = getData("main_key", "");
//		byte[] macKey = null;
//		if (macKeyString == null) {
//			macKey = hexStringToBytes(keyStoreUtils.getWorkKey(tidx)[1]);
//		} else {
//			macKey = DesUtil.desDecrypt(
//					hexStringToBytes(getData("mac_key", "")),
//					hexStringToBytes(macKeyString));
//		}
		
//		byte[] macKey = hexStringToBytes(keyStoreUtils.getWorkKey(tidx)[1]);
		byte[] macKey = hexStringToBytes(macK);
		for (int i = 0; i < part; i++) {
			System.arraycopy(temp, i * 8, desTemp, 0, 8);
			ret = xor(ret, desTemp);
			ret = DesUtil.desEncrypt(ret, macKey);
			System.out.println(bytes2HexString(ret))	;
		}
		return ret;
	}

	
	
	/**
	 * ECB
	 * @param bbb   block
	 * @param kkk  1:body with 64 field 11111111
	 * @param tidx  main key
	 * @return
	 * @throws Exception
	 */
	public static byte[] countMAC_ECB(byte[] bbb, int kkk,String macK) throws Exception {
		byte[] bb = {};
//		KeyStoreUtils keyStoreUtils=new KeyStoreUtils();
		if (kkk == 1) {
			bb = new byte[bbb.length];
			System.arraycopy(bbb, 0, bb, 0, bbb.length);
			bb[9] = (byte) (bb[9] + 1); // 濞屸剝婀�4閻ㄥ嫪缍呴崶锟�		} else {
			bb = new byte[bbb.length - 8];
			System.arraycopy(bbb, 0, bb, 0, bbb.length - 8);
		}
		byte[] ret = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
		int n = 8 - bb.length % 8;
		int part = bb.length / 8 + 1;
		if (n == 8) {
			n = 0;
			part--;
		}
		byte[] temp = new byte[bb.length + n];
		System.arraycopy(bb, 0, temp, 0, bb.length);
		byte[] nextBlock = new byte[8];
//		byte[] macKey = hexStringToBytes(keyStoreUtils.getWorkKey(tidx)[1]);
		byte[] macKey = hexStringToBytes(macK);
//System.out.println("BLOCK:"+bytes2HexString(temp))	;
//System.out.println("MACKEY:"+bytes2HexString(macKey))	;

		for (int i = 0; i < part; i++) {
			System.arraycopy(temp, i * 8, nextBlock, 0, 8);
			ret = xor(ret, nextBlock);
		}
		String xorHex = bytes2HexString(ret);
		String xorHexOne=xorHex.substring(0,8);
		String xorHexTwo=xorHex.substring(8,16);
//System.out.println("xorHex:"+xorHex +"\r\n" +
//				"xorHexOne:"+xorHexOne+"\r\n" +
//				"xorHexTwo:"+xorHexTwo);
		
		ret = DesUtil.desEncrypt(xorHexOne.getBytes(), macKey);
		
		ret = xor(ret, xorHexTwo.getBytes());
		
		ret = DesUtil.desEncrypt(ret, macKey);
		
//System.out.println("MAC RESULT:"+bytes2HexString(ret))	;
		return bytes2HexString(ret).substring(0, 8).getBytes();
	}
	
	



	/**
	 * bytes�?6进制
	 * */
	public static String byte2HexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();

		for (int n = 0; n < bytes.length; n++) {

			if ((bytes[n] & 0xFF) < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(bytes[n] & 0xFF, 16));

			// stmp = (Integer.toHexString(bytes[n] & 0XFF));
			// if (stmp.length() == 1) {
			// hs = hs + "0" + stmp;
			// } else {
			// hs = hs + stmp;
			// }
		}
		return sb.toString().toUpperCase();
		// return hs.toUpperCase();
	}



	/*
	 * 二进�?字节数组,字符,十六进制,BCD编码转换 �?6进制字符串转换成字节数组
	 *
	 * @param hex
	 *
	 * @return
	 */
	public static byte[] hexStringToByte(String hex) {
		hex = hex.toUpperCase();
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}
	private static byte toByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static byte[] xor(byte[] b1, byte[] b2) throws Exception {
		byte[] ret = new byte[8];
		for (int i = 0; i < 8; i++) {
			ret[i] = (byte) (b1[i] ^ b2[i]);
		}
		return ret;
	}

	/**
	 * @see �Զ�����wie�������� ��֧�ֺ���
	 * @param LOrR  L or  R
	 * @param charStr
	 * @param len
	 * @param orgSrc
	 * @return
	 */
	public static String autoFixedLen(String LOrR,char charStr,int len,String orgSrc){
		if(orgSrc==null){
			return null	;
		}
		int strLen=0;
		try {
			strLen = orgSrc.getBytes("GBK").length;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(len <= strLen){
			return orgSrc;
		}

		char[] charReturn = new char[len];
		int minLen = len - strLen;

		if("R".equals(LOrR)){
			char[] charArray = orgSrc.toCharArray();
			for(int i =0 ; i< charArray.length; i++){
				charReturn[i]=charArray[i];
			}
			for(int j =strLen ; j < strLen+minLen; j++){
				charReturn[j]=charStr;
			}
		}
		else if("L".equals(LOrR)){
			char[] charArray = orgSrc.toCharArray();
			for(int j =0 ; j < minLen; j++){
				charReturn[j]=charStr;
			}

			for(int i =0 ; i< charArray.length; i++){
				charReturn[i+minLen]=charArray[i];
			}
		}
		else{
			return orgSrc;
		}

		return new String(charReturn);
	}



	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (toByte(hexChars[pos]) << 4 | toByte(hexChars[pos + 1]));
		}
		return d;
	}


	/**
	 * 字节数组�?6进制字符�?
	 * @param b
	 * @return
	 */
	public static String bytes2HexString( byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String inputData="01002020008000c1801131000000209192363536373138333838393836353031383635313030303301184330763235363331383830303030303032303138d7aac8c33030383332323020202020202020202020202020202020202020202020202020202020202020202020203030303030303038341f500430303033ff280f383938363530313836353130303033ff290836353637313833381f1a0331303223313536000800000001";
			String macK="13E0AE94DCDC682F";
			//ECB
			System.out.println("######################ECB##########################");
			byte[] ii64 = DesUtil.countMAC_ECB(hexStringToByte(inputData), 1,macK);
			System.out.println("ECB:"+byte2HexString(ii64));
			//CBC
			System.out.println("######################CBC##########################");
			ii64 = DesUtil.countMAC(hexStringToByte(inputData), 1, macK);
			System.out.println("CBC:"+byte2HexString(ii64));


		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
