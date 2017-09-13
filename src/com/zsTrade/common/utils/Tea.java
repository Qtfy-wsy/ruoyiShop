package com.zsTrade.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.CRC32;

import net.sf.json.JSONArray;



public class Tea {

	int[] KEY = new int[]{0x37424538, 0x39393030, 0x44454630, 0x44313630};
    //����
    public byte[] encrypt(byte[] content, int offset, int[] key, int times){//timesΪ��������
        int[] tempInt = byteToInt(content, offset);
        int y = tempInt[0], z = tempInt[1], sum = 0, i;
        int delta=0x9e3779b9; //�����㷨��׼���ֵ
        int a = key[0], b = key[1], c = key[2], d = key[3]; 
        for (i = 0; i < times; i++) {   
            sum += delta;
            y += ((z<<4) + a) ^ (z + sum) ^ ((z>>5) + b);
            z += ((y<<4) + c) ^ (y + sum) ^ ((y>>5) + d);
        }
        tempInt[0]=y;
        tempInt[1]=z; 
        return intToByte(tempInt, 0);
    }
    //����
    public byte[] decrypt(byte[] encryptContent, int offset, int[] key, int times){
        int[] tempInt = byteToInt(encryptContent, offset);
        int y = tempInt[0], z = tempInt[1], sum = 0, i;
        int delta=0x9e3779b9; //�����㷨��׼���ֵ
        int a = key[0], b = key[1], c = key[2], d = key[3];
        if (times == 32)
            sum = 0xC6EF3720; /* delta << 5*/
        else if (times == 16)
            sum = 0xE3779B90; /* delta << 4*/
        else
            sum = delta * times;

        for(i = 0; i < times; i++) { 
            z -= ((y<<4) + c) ^ (y + sum) ^ ((y>>5) + d);
            y -= ((z<<4) + a) ^ (z + sum) ^ ((z>>5) + b);
            sum -= delta; 
        }
        tempInt[0] = y;
        tempInt[1] = z;

        return intToByte(tempInt, 0);
    }
    //byte[]�����ת��int[]�����
    private static int[] byteToInt(byte[] content, int offset){

        int[] result = new int[content.length >> 2];//����2��n�η� == ����nλ �� content.length / 4 == content.length >> 2
        for(int i = 0, j = offset; j < content.length; i++, j += 4){
            result[i] = transform(content[j]) | transform(content[j + 1]) << 8 |
            transform(content[j + 2]) << 16 | (int)content[j+3] << 24;
        }
        return result;
        
    }
    //int[]�����ת��byte[]�����
    private byte[] intToByte(int[] content, int offset){
        byte[] result = new byte[content.length << 2];//����2��n�η� == ����nλ �� content.length * 4 == content.length << 2
        for(int i = 0, j = offset; j < result.length; i++, j += 4){
            result[j] = (byte)(content[i] & 0xff);
            result[j + 1] = (byte)((content[i] >> 8) & 0xff);
            result[j + 2] = (byte)((content[i] >> 16) & 0xff);
            result[j+3] = (byte)((content[i] >> 24) & 0xff);
        }
        return result;
    }
    //��ĳ�ֽ�Ϊ�������轫��ת���޷������
    private static int transform(byte temp){
    //	System.out.println(temp);
        int tempInt = (int)temp;
        if(tempInt < 0){
            tempInt += 256;
        }
        return tempInt;
    }
    
    public static String toMD5(String plainText) {
        try {
           //���ʵ��ָ��ժҪ�㷨�� MessageDigest ����
           MessageDigest md = MessageDigest.getInstance("MD5");  
           //ʹ��ָ�����ֽ��������ժҪ��
           md.update(plainText.getBytes());
           //ͨ��ִ���������֮������ղ�����ɹ�ϣ���㡣
           byte b[] = md.digest();
           //��ɾ����md5���뵽buf����
           int i;
           StringBuffer buf = new StringBuffer("");
           for (int offset = 0; offset < b.length; offset++) {
             i = b[offset];
             if (i < 0)
               i += 256;
             if (i < 16)
               buf.append("0");
             buf.append(Integer.toHexString(i));
           }
         //  System.out.println("32λ: " + buf.toString().toUpperCase());// 32λ�ļ���
           return buf.toString().toUpperCase();
          // System.out.println("16λ: " + buf.toString().substring(8, 24));// 16λ�ļ��ܣ���ʵ����32λ���ܺ�Ľ�ȡ
        } catch (Exception e) {
          e.printStackTrace();
          return null;
        }
        
      }
    
    public static String crc32(String value){
    	CRC32 crc = new CRC32();
    	crc.update(value.getBytes());
    	String val= Long.toHexString(crc.getValue());
    	String realV = null;
    	if(val.length()!=8){
    		int count=8-val.length();
    		if(count==1){
    			realV="0"+val;
    		}else if(count==2){
    			realV="00"+val;
    		}else if(count==3){
    			realV="000"+val;
    		}else if(count==4){
    			realV="0000"+val;
    		}else if(count==5){
    			realV="00000"+val;
    		}
    		
    	}else{
    		return val;
    	}
    	return realV;
    }
    public static int[] StringToint(String str) {
    	int[] ii=new int[4];
    	byte[] cc=StringToByte(str);
    	String i1=String.format("%02X", cc[3])+String.format("%02X", cc[2])+String.format("%02X", cc[1])+String.format("%02X", cc[0]);
    	String i2=String.format("%02X", cc[7])+String.format("%02X", cc[6])+String.format("%02X", cc[5])+String.format("%02X", cc[4]);
    	String i3=String.format("%02X", cc[11])+String.format("%02X", cc[10])+String.format("%02X", cc[9])+String.format("%02X", cc[8]);
    	String i4=String.format("%02X", cc[15])+String.format("%02X", cc[14])+String.format("%02X", cc[13])+String.format("%02X", cc[12]);
    	
    	ii[0]=Integer.parseInt(i1,16);
    	ii[1]=Integer.parseInt(i2,16);
    	ii[2]=Integer.parseInt(i3,16);
    	ii[3]=Integer.parseInt(i4,16);
    	return ii;

  	 }
    public static String tea2(String value,String key){
    	 Tea tea = new Tea();
    	 int[] KEY = StringToint(key);
    	// int[] KEY = new int[]{0x37424538, 0x39393030, 0x44454630, 0x44313630};
    	 byte[] secretInfo = tea.encrypt(value.toUpperCase().getBytes(), 0, KEY, 32);
    	 StringBuilder sb_test = new StringBuilder();
    	 sb_test.append("[");
     	for(byte bb:secretInfo){
     		//sb_test.append(String.format("%02X", bb));
     		sb_test.append(bb);
     		sb_test.append(",");
     	}
     	sb_test.substring(0, sb_test.length()-1);
     	sb_test.append("]");
    // 	System.out.println(sb_test);
     	return sb_test.toString();
    }
    public static int byte2Int(byte[] b) { 
        int l = 0; 
        l = b[0]; 
        l &= 0xff; 
        l |= ((int) b[1] << 8); 
        l &= 0xffff; 
        l |= ((int) b[2] << 16); 
        l &= 0xffffff; 
        l |= ((int) b[3] << 24); 
        l &= 0xffffffff; 
        return l; 
    }
    public static int[] tea3(String value,String key) throws UnsupportedEncodingException{
   	 Tea tea = new Tea();
   	 int[] KEY = StringToint(key);
   	// int[] KEY = new int[]{0x37424538, 0x39393030, 0x44454630, 0x44313630};
   	 byte[] secretInfo = tea.encrypt(value.toUpperCase().getBytes(), 0, KEY, 32);
 
   	 int[] sa =new int[8];
   	 StringBuilder sb_test = new StringBuilder();
    	for(int i=0;i<secretInfo.length;i++){
    	//	sb_test.append(String.format("%02X", bb));
//    		sb_test.append(bb);
//    		sb_test.append(" ");
    		sa[i]=secretInfo[i];
    	}
    	return sa;
   }
    public static byte[] tea1(String value,String key){
   	 Tea tea = new Tea();
   	 int[] KEY = StringToint(key);
   	// int[] KEY = new int[]{0x37424538, 0x39393030, 0x44454630, 0x44313630};
   	 byte[] secretInfo = tea.encrypt(value.toUpperCase().getBytes(), 0, KEY, 32);
   	 
    	return secretInfo;
   }
    public static void main(String[] args) {
    	CRC32 crc32 = new CRC32();
    	crc32.update("hello-world".getBytes());
    	System.out.println(crc32.getValue());
    	
    	System.out.println(Tea.crc32("HUADI_GZ6200B1FB3E48588FCB35C3BA3ACA636858280CA175001"));
    //	System.out.println(Tea.tea2("3A1B31BD", "8EB700990FED061D"));
    //	String xx=Tea.tea2("3A1B31BD", "8EB700990FED061D");
    //	JSONArray array1=JSONArray.fromObject(xx);
    //	System.out.println(array1);
	}
    public static void main11(String[] args) {
    	//System.out.println(Integer.toHexString(37424538));
    	//System.out.println(Integer.parseInt("37424538",16));
    	debugLogByte(StringToByte("8EB700990FED061D"));
    	
    	//927089976
//    	System.out.println(getCurrentDateNum());
    	int[] ii=byteToInt(StringToByte("8EB700990FED061D"),0);
    	for(int bb:ii){
    	//	System.out.println(bb);
    	}
    	byte[] cc=StringToByte("8EB700990FED061D");
    	int[] iii =StringToint("8EB700990FED061D");
    	for(int bb:iii){
    	//		System.out.println(bb);
    		}
//    	System.out.println(String.format("%02X", cc[3])+String.format("%02X", cc[2])+String.format("%02X", cc[1])+String.format("%02X", cc[0]));
//    System.out.println(Tea.tea1("3A1B31BD", "8EB700990FED061D"));
    }
    public static void main2(String[] args) {
    
    	  Tea tea = new Tea();
    	  int[] KEY = new int[]{0x37424538, 0x39393030, 0x44454630, 0x44313630};
//    	  TEA(CRC32('2CADC7DBD4E4FB2DA0FB8A2FD2352AF6432C481FCDD45CB2') )=TEA(
//    			  '1DBD2E94')=[0x3D, 0x4F, 0x99, 0x1E, 0x3F, 0xA7, 0x2A, 0xAE]
    	  
//    	  CRC32('2CADC7DBD4E4FB2DA0FB8A2FD2352AF6432C481FCDD45CB2')=
//    	  '1DBD2E94'
    	CRC32 crc = new CRC32();
    	crc.update("2CADC7DBD4E4FB2DA0FB8A2FD2352AF6432C481FCDD45CB2".getBytes());
    //	System.out.println(crc.getValue());
//    	System.out.println(Long.toBinaryString(crc.getValue()));
//    	System.out.println(Long.toOctalString(crc.getValue()));
    	String crcValue = Long.toHexString(crc.getValue());
    //	crc.update(crcValue.getBytes());
    //	System.out.println(Long.toHexString(crc.getValue()));
    //	System.out.println(crc.getValue());
    //	System.out.println(crcValue);
    	//byte[] value = ByteUtil.getBytes(crc.getValue());
   	 byte[] secretInfo = tea.encrypt("xxxxxxxxxxxxxxxxxxxxxxxx2".toUpperCase().getBytes(), 0, KEY, 32);
   	 System.out.println("secretInfo.tostring = "+new String(secretInfo));
   	// System.out.println(arg0);
   	debugLogByte1(secretInfo);

	}
    public static void debugLogByte1(byte[] b){
    	StringBuilder sb_test = new StringBuilder();
    	for(byte bb:b){
    		sb_test.append(String.format("%02X", bb));
    		sb_test.append(" ");
    	}
    //	System.out.println(sb_test.toString());
    }
    
public static void main1(String[] args){

	int[] KEY = new int[]{0x37424538, 0x39393030, 0x44454630, 0x44313630};
        Tea tea = new Tea();
//        
        byte[] info = new byte[]{
              //1,2,3,4,5,6,7,8  
                50,67,65,68,67,55,68,66
        };
       
        for(byte i : info)
         //   System.out.print(i + " ");
        System.out.println();
        byte[] secretInfo = tea.encrypt(info, 0, KEY, 32);
      //  System.out.print("���ܺ����ݣ�");
        StringBuilder sb_test = new StringBuilder();
        for(byte i : secretInfo){
        	sb_test.append(String.format("%02X", i));
			sb_test.append(" ");
        }
     //   System.out.println(sb_test.toString());
        
        byte[] decryptInfo = tea.decrypt(secretInfo, 0, KEY, 32);
        System.out.print("���ܺ����ݣ�");
        for(byte i : decryptInfo)
            System.out.print(i + " ");
	

   }
public static void debugLogByte(byte[] b){
	StringBuilder sb_test = new StringBuilder();
	for(byte bb:b){
		sb_test.append(String.format("%02X", bb));
		sb_test.append(" ");
	}
	//System.out.println(sb_test.toString());
}
public static byte getByte(String b){
	int index = Integer.parseInt(b,16);
		if(index >= 80){
			short s = Short.parseShort(b,16);
			
			return (byte)s;
		}else{
			return Byte.parseByte(b, 16);
		}
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
        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        
    }
    return d;
}
public static byte[] StringToByte(String str) {
	  int len = str.length();
	  byte[] bytes = new byte[len];
	  for(int i=0; i<len; i++) {
	   bytes[i] = (byte)(str.charAt(i));
	  } 
	  return bytes;
	 }
/** 
* 随机产生一个四位的随机数据 
* @return 
*/ 
public static String getRandomInteger() 
{ 
int temp=(int) (Math.random()*9999); 
return String.valueOf(temp); 
} 

/** 
* 拿当前的时间为一个序列号 
* @return 
*/ 
public static String getCurrentDateNum() 
{ 
Date dttime= new Date(); 
SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
String currentTime = sdf.format(dttime); 
String str=currentTime+getRandomInteger(); 
return str; 
} 
private static byte charToByte(char c) {
    return (byte) "0123456789ABCDEF".indexOf(c);
}
}