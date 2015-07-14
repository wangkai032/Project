package com.helloweenvsfei.util.ip;

/*
 * Created on 2004-8-4
 *
 */


import java.io.UnsupportedEncodingException;

/**
 * @author LJ-silver
 */
public class Utils {
    /**
     * �qip���r��Φ��o��r�`�}�C�Φ�
     * @param ip �r��Φ���ip
     * @return �r�`�}�C�Φ���ip
     */
    public static byte[] getIpByteArrayFromString(String ip) {
        byte[] ret = new byte[4];
        java.util.StringTokenizer st = new java.util.StringTokenizer(ip, ".");
        try {
            ret[0] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
            ret[1] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
            ret[2] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
            ret[3] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ret;
    }
    
    public static void main(String args[]){
         byte[] a=getIpByteArrayFromString(args[0]);
          for(int i=0;i<a.length;i++)
                System.out.println(a[i]);
          System.out.println(getIpStringFromBytes(a)); 
    }
    /**
     * ���l�r��i��ѽX�ഫ�A�p�G���ѡA�Ǧ^��l���r��
     * @param s ��l�r��
     * @param srcEncoding ���ѽX�覡
     * @param destEncoding �ؼиѽX�覡
     * @return �ഫ�ѽX�᪺�r��A���ѶǦ^��l�r��
     */
    public static String getString(String s, String srcEncoding, String destEncoding) {
        try {
            return new String(s.getBytes(srcEncoding), destEncoding);
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }
    
    /**
     * �ھڬY�ظѽX�覡�N�r�`�}�C�ഫ���r��
     * @param b �r�`�}�C
     * @param encoding �ѽX�覡
     * @return �p�Gencoding���䴩�A�Ǧ^�@�ӯʬٸѽX���r��
     */
    public static String getString(byte[] b, String encoding) {
        try {
            return new String(b, encoding);
        } catch (UnsupportedEncodingException e) {
            return new String(b);
        }
    }
    
    /**
     * �ھڬY�ظѽX�覡�N�r�`�}�C�ഫ���r��
     * @param b �r�`�}�C
     * @param offset �n�ഫ���_�l��m
     * @param len �n�ഫ������
     * @param encoding �ѽX�覡
     * @return �p�Gencoding���䴩�A�Ǧ^�@�ӯʬٸѽX���r��
     */
    public static String getString(byte[] b, int offset, int len, String encoding) {
        try {
            return new String(b, offset, len, encoding);
        } catch (UnsupportedEncodingException e) {
            return new String(b, offset, len);
        }
    }
    
    /**
     * @param ip ip���r�`�}�C�Φ�
     * @return �r��Φ���ip
     */
    public static String getIpStringFromBytes(byte[] ip) {
    	StringBuffer sb = new StringBuffer();
    	sb.append(ip[0] & 0xFF);
    	sb.append('.');   	
    	sb.append(ip[1] & 0xFF);
    	sb.append('.');   	
    	sb.append(ip[2] & 0xFF);
    	sb.append('.');   	
    	sb.append(ip[3] & 0xFF);
    	return sb.toString();
    }

} 


 
