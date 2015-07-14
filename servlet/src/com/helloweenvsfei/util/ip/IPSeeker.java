package com.helloweenvsfei.util.ip;

/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 luma <stubma@163.com>
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;




/**
 * <pre>
 * �Ψ�Ū��QQwry.dat�ɮסA�H�ھ�ip��o�n�ͦ�m�AQQwry.dat���榡�O
 * �@. �ɮ��Y�A�@8�r�`
 * 	   1. �Ĥ@�Ӱ_�lIP�����ﰾ���A 4�r�`
 *     2. �̫�@�Ӱ_�lIP�����ﰾ���A 4�r�`
 * �G. "�����a�}/��a/�ϰ�"�O����
 *     �|�r�`ip�a�}��򪺨C�@���O��������ӳ���
 *     1. ��a�O��
 *     2. �a�ϰO��
 *     ���O�a�ϰO���O���@�w�����C�ӥB��a�O���M�a�ϰO��������اΦ�
 *     1. �H0�������r��
 *     2. 4�Ӧr�`�A�@�Ӧr�`�i�ର0x1��0x2
 * 		  a. ��0x1�ɡA��ܦb���ﰾ�����ٸ�ۤ@�Ӱϰ쪺�O���A�`�N�O���ﰾ������A�Ӥ��O�o�|�Ӧr�`����
 *        b. ��0x2�ɡA��ܦb���ﰾ����S���ϰ�O��
 *        ���ެ�0x1�٬O0x2�A��T�Ӧr�`���O��ڰ�a�W���ɮפ����ﰾ��
 * 		  �p�G�O�a�ϰO���A0x1�M0x2���t�q�����A���O�p�G�X�{�o��Ӧr�`�A�]�֩w�O���3�Ӧr�`�����A�p�G���O
 *        �h��0�����r��
 * �T. "�_�l�a�}/�����a�}����"�O����
 *     1. �C���O��7�r�`�A���Ӱ_�l�a�}�q�p��j�ƦC
 *        a. �_�lIP�a�}�A4�r�`
 *        b. ����ip�a�}�����ﰾ���A3�r�`
 *
 * �`�N�A�o���ɮ׸̪�ip�a�}�M�Ҧ��������q���ĥ�little-endian�榡�A��java�O�ĥ�
 * big-endian�榡���A�n�`�N�ഫ
 * </pre>
 *
 * @author ���Y��
 */
public class IPSeeker {
	/**
	 * <pre>
	 * �Ψӫʸ�ip������T�A�ثe�u����Ӧr�q�Aip�Ҧb����a�M�a��
	 * </pre>
	 *
	 * @author ���Y��
	 */
	private class IPLocation {
		public String country;
		public String area;

		public IPLocation() {
		    country = area = "";
		}

		public IPLocation getCopy() {
		    IPLocation ret = new IPLocation();
		    ret.country = country;
		    ret.area = area;
		    return ret;
		}
	}

	private static final File IP_FILE = new File(IPSeeker.class.getResource("").getFile(), "QQWry.dat");

	// �@�ǩT�w�`�ơA�Ҧp�O�����׵���
	private static final int IP_RECORD_LENGTH = 7;
	private static final byte AREA_FOLLOWED = 0x01;
	private static final byte NO_AREA = 0x2;

 	// �ΨӰ���cache�A�d�ߤ@��ip�ɭ����˵�cache�A�H��֤����n�����ƴM��
	private Hashtable ipCache;
	// �H���ɮצs����
	private RandomAccessFile ipFile;
	// �O����M�g�ɮ�
	private MappedByteBuffer mbb;
	// ��@�Ҧ����
	private static IPSeeker instance = new IPSeeker();
	// �_�l�a�Ϫ��}�l�M���������ﰾ��
	private long ipBegin, ipEnd;
	// �����@�Ĳv�ӱĥΪ��{���ܼ�
	private IPLocation loc;
	private byte[] buf;
	private byte[] b4;
	private byte[] b3;

	/**
	 * �p���غc���
	 */
	private IPSeeker()  {
		ipCache = new Hashtable();
		loc = new IPLocation();
		buf = new byte[100];
		b4 = new byte[4];
		b3 = new byte[3];
		try {
			ipFile = new RandomAccessFile(IP_FILE, "r");
		} catch (FileNotFoundException e) {
                        System.out.println(IP_FILE.getAbsolutePath());
                        System.out.println(IP_FILE);
			System.out.println("IP�a�}��T�ɮרS�����AIP��ܥ\��N�L�k�ϥ�");
			ipFile = null;

		}
		// �p�G�}���ɮצ��\�AŪ���ɮ��Y��T
		if(ipFile != null) {
			try {
				ipBegin = readLong4(0);
				ipEnd = readLong4(4);
				if(ipBegin == -1 || ipEnd == -1) {
					ipFile.close();
					ipFile = null;
				}
			} catch (IOException e) {
				System.out.println("IP�a�}��T�ɮ׮榡�����~�AIP��ܥ\��N�L�k�ϥ�");
				ipFile = null;
			}
		}
	}

	/**
	 * @return ��@���
	 */
	public static IPSeeker getInstance() {
		return instance;
	}

	/**
	 * ���w�@�Ӧa�I���������W�r�A�o��@�t�C�]�ts�l�r�ꪺIP�d��O��
	 * @param s �a�I�l�r��
	 * @return �]�tIPEntry������List
	 */
	public List getIPEntriesDebug(String s) {
	    List ret = new ArrayList();
	    long endOffset = ipEnd + 4;
	    for(long offset = ipBegin + 4; offset <= endOffset; offset += IP_RECORD_LENGTH) {
	        // Ū������IP����
	        long temp = readLong3(offset);
	        // �p�Gtemp������-1�AŪ��IP���a�I��T
	        if(temp != -1) {
	            IPLocation loc = getIPLocation(temp);
	            // �P�_�O�_�o�Ӧa�I�̭��]�t�Fs�l�r��A�p�G�]�t�F�A�W�[�o�ӰO����List���A�p�G�S���A�~��
	            if(loc.country.indexOf(s) != -1 || loc.area.indexOf(s) != -1) {
	                IPEntry entry = new IPEntry();
	                entry.country = loc.country;
	                entry.area = loc.area;
	                // �o��_�lIP
	    	        readIP(offset - 4, b4);
	                entry.beginIp = Utils.getIpStringFromBytes(b4);
	                // �o�쵲��IP
	                readIP(temp, b4);
	                entry.endIp = Utils.getIpStringFromBytes(b4);
	                // �W�[�ӰO��
	                ret.add(entry);
	            }
	        }
	    }
	    return ret;
	}

	/**
	 * ���w�@�Ӧa�I���������W�r�A�o��@�t�C�]�ts�l�r�ꪺIP�d��O��
	 * @param s �a�I�l�r��
	 * @return �]�tIPEntry������List
	 */
	public List getIPEntries(String s) {
	    List ret = new ArrayList();
	    try {
	        // �M�gIP��T�ɮר�O���餤
	        if(mbb == null) {
			    FileChannel fc = ipFile.getChannel();
	            mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, ipFile.length());
	            mbb.order(ByteOrder.LITTLE_ENDIAN);
	        }

		    int endOffset = (int)ipEnd;
            for(int offset = (int)ipBegin + 4; offset <= endOffset; offset += IP_RECORD_LENGTH) {
                int temp = readInt3(offset);
                if(temp != -1) {
    	            IPLocation loc = getIPLocation(temp);
    	            // �P�_�O�_�o�Ӧa�I�̭��]�t�Fs�l�r��A�p�G�]�t�F�A�W�[�o�ӰO����List���A�p�G�S���A�~��
    	            if(loc.country.indexOf(s) != -1 || loc.area.indexOf(s) != -1) {
    	                IPEntry entry = new IPEntry();
    	                entry.country = loc.country;
    	                entry.area = loc.area;
    	                // �o��_�lIP
    	    	        readIP(offset - 4, b4);
    	                entry.beginIp = Utils.getIpStringFromBytes(b4);
    	                // �o�쵲��IP
    	                readIP(temp, b4);
    	                entry.endIp = Utils.getIpStringFromBytes(b4);
    	                // �W�[�ӰO��
    	                ret.add(entry);
    	            }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ret;
	}

	/**
	 * �q�O����M�g�ɮת�offset��m�}�l��3�Ӧr�`Ū���@��int
	 * @param offset
	 * @return
	 */
	private int readInt3(int offset) {
	    mbb.position(offset);
	    return mbb.getInt() & 0x00FFFFFF;
	}

	/**
	 * �q�O����M�g�ɮת��ثe��m�}�l��3�Ӧr�`Ū���@��int
	 * @return
	 */
	private int readInt3() {
	    return mbb.getInt() & 0x00FFFFFF;
	}

	/**
	 * �ھ�IP�o���a�W
	 * @param ip ip���r�`�}�C�Φ�
	 * @return ��a�W�r��
	 */
	public String getCountry(byte[] ip) {
		// �ˬdip�a�}�ɮ׬O�_���`
		if(ipFile == null) return "���~��IP��Ʈw�ɮ�";
		// �x�sip�A�ഫip�r�`�}�C���r��Φ�
		String ipStr = Utils.getIpStringFromBytes(ip);
		// ���ˬdcache���O�_�w�g�]�t���o��ip�����G�A�S���A�j���ɮ�
		if(ipCache.containsKey(ipStr)) {
			IPLocation loc = (IPLocation)ipCache.get(ipStr);
			return loc.country;
		} else {
			IPLocation loc = getIPLocation(ip);
			ipCache.put(ipStr, loc.getCopy());
			return loc.country;
		}
	}

	/**
	 * �ھ�IP�o���a�W
	 * @param ip IP���r��Φ�
	 * @return ��a�W�r��
	 */
	public String getCountry(String ip) {
	    return getCountry(Utils.getIpByteArrayFromString(ip));
	}

	/**
	 * �ھ�IP�o��a�ϦW
	 * @param ip ip���r�`�}�C�Φ�
	 * @return �a�ϦW�r��
	 */
	public String getArea(byte[] ip) {
		// �ˬdip�a�}�ɮ׬O�_���`
		if(ipFile == null) return "���~��IP��Ʈw�ɮ�";
		// �x�sip�A�ഫip�r�`�}�C���r��Φ�
		String ipStr = Utils.getIpStringFromBytes(ip);
		// ���ˬdcache���O�_�w�g�]�t���o��ip�����G�A�S���A�j���ɮ�
		if(ipCache.containsKey(ipStr)) {
			IPLocation loc = (IPLocation)ipCache.get(ipStr);
			return loc.area;
		} else {
			IPLocation loc = getIPLocation(ip);
			ipCache.put(ipStr, loc.getCopy());
			return loc.area;
		}
	}

	/**
	 * �ھ�IP�o��a�ϦW
	 * @param ip IP���r��Φ�
	 * @return �a�ϦW�r��
	 */
	public String getArea(String ip) {
	    return getArea(Utils.getIpByteArrayFromString(ip));
	}

	/**
	 * �ھ�ip�j��ip��T�ɮסA�o��IPLocation���c�A�ҷj����ip�ѼƱq������ip���o��
	 * @param ip �n�d�ߪ�IP
	 * @return IPLocation���c
	 */
	private IPLocation getIPLocation(byte[] ip) {
		IPLocation info = null;
		long offset = locateIP(ip);
		if(offset != -1)
			info = getIPLocation(offset);
		if(info == null) {
			info = new IPLocation();
			info.country = "������a";
			info.area = "�����a��";
		}
		return info;
	}

	/**
	 * �qoffset��mŪ��4�Ӧr�`���@��long�A�]��java��big-endian�榡�A�ҥH�S��k
	 * �ΤF�o��@�Ө�ƨӰ��ഫ
	 * @param offset
	 * @return Ū����long�ȡA�Ǧ^-1���Ū���ɮץ���
	 */
	private long readLong4(long offset) {
		long ret = 0;
		try {
			ipFile.seek(offset);
			ret |= (ipFile.readByte() & 0xFF);
			ret |= ((ipFile.readByte() << 8) & 0xFF00);
			ret |= ((ipFile.readByte() << 16) & 0xFF0000);
			ret |= ((ipFile.readByte() << 24) & 0xFF000000);
			return ret;
		} catch (IOException e) {
			return -1;
		}
	}

	/**
	 * �qoffset��mŪ��3�Ӧr�`���@��long�A�]��java��big-endian�榡�A�ҥH�S��k
	 * �ΤF�o��@�Ө�ƨӰ��ഫ
	 * @param offset
	 * @return Ū����long�ȡA�Ǧ^-1���Ū���ɮץ���
	 */
	private long readLong3(long offset) {
		long ret = 0;
		try {
			ipFile.seek(offset);
			ipFile.readFully(b3);
			ret |= (b3[0] & 0xFF);
			ret |= ((b3[1] << 8) & 0xFF00);
			ret |= ((b3[2] << 16) & 0xFF0000);
			return ret;
		} catch (IOException e) {
			return -1;
		}
	}

	/**
	 * �q�ثe��mŪ��3�Ӧr�`�ഫ��long
	 * @return
	 */
	private long readLong3() {
		long ret = 0;
		try {
			ipFile.readFully(b3);
			ret |= (b3[0] & 0xFF);
			ret |= ((b3[1] << 8) & 0xFF00);
			ret |= ((b3[2] << 16) & 0xFF0000);
			return ret;
		} catch (IOException e) {
			return -1;
		}
	}

	/**
	 * �qoffset��mŪ���|�Ӧr�`��ip�a�}��Jip�}�C���AŪ���᪺ip��big-endian�榡�A���O
	 * �ɮפ��Olittle-endian�Φ��A�N�|�i���ഫ
	 * @param offset
	 * @param ip
	 */
	private void readIP(long offset, byte[] ip) {
		try {
			ipFile.seek(offset);
			ipFile.readFully(ip);
			byte temp = ip[0];
			ip[0] = ip[3];
			ip[3] = temp;
			temp = ip[1];
			ip[1] = ip[2];
			ip[2] = temp;
		} catch (IOException e) {
		    System.out.println(e.getMessage());
		}
	}

	/**
	 * �qoffset��mŪ���|�Ӧr�`��ip�a�}��Jip�}�C���AŪ���᪺ip��big-endian�榡�A���O
	 * �ɮפ��Olittle-endian�Φ��A�N�|�i���ഫ
	 * @param offset
	 * @param ip
	 */
	private void readIP(int offset, byte[] ip) {
	    mbb.position(offset);
	    mbb.get(ip);
		byte temp = ip[0];
		ip[0] = ip[3];
		ip[3] = temp;
		temp = ip[1];
		ip[1] = ip[2];
		ip[2] = temp;
	}

	/**
	 * ��������ip�MbeginIp����A�`�N�o��beginIp�Obig-endian��
	 * @param ip �n�d�ߪ�IP
	 * @param beginIp �M�Q�d��IP�ۤ����IP
	 * @return �۵��Ǧ^0�Aip�j��beginIp�h�Ǧ^1�A�p��Ǧ^-1�C
	 */
	private int compareIP(byte[] ip, byte[] beginIp) {
		for(int i = 0; i < 4; i++) {
			int r = compareByte(ip[i], beginIp[i]);
			if(r != 0)
				return r;
		}
		return 0;
	}

	/**
	 * ����byte��@�L�Ÿ��ƶi����
	 * @param b1
	 * @param b2
	 * @return �Yb1�j��b2�h�Ǧ^1�A�۵��Ǧ^0�A�p��Ǧ^-1
	 */
	private int compareByte(byte b1, byte b2) {
		if((b1 & 0xFF) > (b2 & 0xFF)) // ����O�_�j��
			return 1;
		else if((b1 ^ b2) == 0)// �P�_�O�_�۵�
			return 0;
		else
			return -1;
	}

	/**
	 * �o�Ӥ�k�N�ھ�ip�����e�A�w���]�t�o��ip��a�a�Ϫ��O���B�A�Ǧ^�@�ӵ��ﰾ��
	 * ��k�ϥΤG���k�M��C
	 * @param ip �n�d�ߪ�IP
	 * @return �p�G���F�A�Ǧ^����IP�������A�p�G�S�����A�Ǧ^-1
	 */
	private long locateIP(byte[] ip) {
		long m = 0;
		int r;
		// ����Ĥ@��ip��
		readIP(ipBegin, b4);
		r = compareIP(ip, b4);
		if(r == 0) return ipBegin;
		else if(r < 0) return -1;
		// �}�l�G���j��
		for(long i = ipBegin, j = ipEnd; i < j; ) {
			m = getMiddleOffset(i, j);
			readIP(m, b4);
			r = compareIP(ip, b4);
			// log.debug(Utils.getIpStringFromBytes(b));
			if(r > 0)
				i = m;
			else if(r < 0) {
				if(m == j) {
					j -= IP_RECORD_LENGTH;
					m = j;
				} else
					j = m;
			} else
				return readLong3(m + 4);
		}
		// �p�G�`�������F�A����i�Mj���w�O�۵����A�o�ӰO�����̥i�઺�O���A���O�ëD
		//     �֩w�N�O�A�٭n�ˬd�@�U�A�p�G�O�A�N�Ǧ^�����a�}�Ϫ����ﰾ��
		m = readLong3(m + 4);
		readIP(m, b4);
		r = compareIP(ip, b4);
		if(r <= 0) return m;
		else return -1;
	}

	/**
	 * �o��begin�����Mend����������m�O��������
	 * @param begin
	 * @param end
	 * @return
	 */
	private long getMiddleOffset(long begin, long end) {
		long records = (end - begin) / IP_RECORD_LENGTH;
		records >>= 1;
		if(records == 0) records = 1;
		return begin + records * IP_RECORD_LENGTH;
	}

	/**
	 * ���w�@��ip��a�a�ϰO���������A�Ǧ^�@��IPLocation���c
	 * @param offset
	 * @return
	 */
	private IPLocation getIPLocation(long offset) {
		try {
			// ���L4�r�`ip
			ipFile.seek(offset + 4);
			// Ū���Ĥ@�Ӧr�`�P�_�O�_�лx�r�`
			byte b = ipFile.readByte();
			if(b == AREA_FOLLOWED) {
				// Ū����a����
				long countryOffset = readLong3();
				// ���D�ܰ����B
				ipFile.seek(countryOffset);
				// �A�ˬd�@���лx�r�`�A�]���o�ӮɭԳo�Ӧa�褴�M�i��O�ӭ��s�ɦV
				b = ipFile.readByte();
				if(b == NO_AREA) {
					loc.country = readString(readLong3());
					ipFile.seek(countryOffset + 4);
				} else
					loc.country = readString(countryOffset);
				// Ū���a�ϼлx
				loc.area = readArea(ipFile.getFilePointer());
			} else if(b == NO_AREA) {
				loc.country = readString(readLong3());
				loc.area = readArea(offset + 8);
			} else {
				loc.country = readString(ipFile.getFilePointer() - 1);
				loc.area = readArea(ipFile.getFilePointer());
			}
			return loc;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * @param offset
	 * @return
	 */
	private IPLocation getIPLocation(int offset) {
		// ���L4�r�`ip
	    mbb.position(offset + 4);
		// Ū���Ĥ@�Ӧr�`�P�_�O�_�лx�r�`
		byte b = mbb.get();
		if(b == AREA_FOLLOWED) {
			// Ū����a����
			int countryOffset = readInt3();
			// ���D�ܰ����B
			mbb.position(countryOffset);
			// �A�ˬd�@���лx�r�`�A�]���o�ӮɭԳo�Ӧa�褴�M�i��O�ӭ��s�ɦV
			b = mbb.get();
			if(b == NO_AREA) {
				loc.country = readString(readInt3());
				mbb.position(countryOffset + 4);
			} else
				loc.country = readString(countryOffset);
			// Ū���a�ϼлx
			loc.area = readArea(mbb.position());
		} else if(b == NO_AREA) {
			loc.country = readString(readInt3());
			loc.area = readArea(offset + 8);
		} else {
			loc.country = readString(mbb.position() - 1);
			loc.area = readArea(mbb.position());
		}
		return loc;
	}

	/**
	 * �qoffset�����}�l�ѪR�᭱���r�`�AŪ�X�@�Ӧa�ϦW
	 * @param offset
	 * @return �a�ϦW�r��
	 * @throws IOException
	 */
	private String readArea(long offset) throws IOException {
		ipFile.seek(offset);
		byte b = ipFile.readByte();
		if(b == 0x01 || b == 0x02) {
			long areaOffset = readLong3(offset + 1);
			if(areaOffset == 0)
				return "�����a��";
			else
				return readString(areaOffset);
		} else
			return readString(offset);
	}

	/**
	 * @param offset
	 * @return
	 */
	private String readArea(int offset) {
		mbb.position(offset);
		byte b = mbb.get();
		if(b == 0x01 || b == 0x02) {
			int areaOffset = readInt3();
			if(areaOffset == 0)
				return "�����a��";
			else
				return readString(areaOffset);
		} else
			return readString(offset);
	}

	/**
	 * �qoffset�����BŪ���@�ӥH0�������r��
	 * @param offset
	 * @return Ū�����r��A�X���Ǧ^�Ŧr��
	 */
	private String readString(long offset) {
		try {
			ipFile.seek(offset);
			int i;
			for(i = 0, buf[i] = ipFile.readByte(); buf[i] != 0; buf[++i] = ipFile.readByte());
			if(i != 0)
			    return Utils.getString(buf, 0, i, "GBK");
		} catch (IOException e) {
		    System.out.println(e.getMessage());
		}
		return "";
	}

	/**
	 * �q�O����M�g�ɮת�offset��m�o��@��0�����r��
	 * @param offset
	 * @return
	 */
	private String readString(int offset) {
	    try {
			mbb.position(offset);
			int i;
			for(i = 0, buf[i] = mbb.get(); buf[i] != 0; buf[++i] = mbb.get());
			if(i != 0)
			    return Utils.getString(buf, 0, i, "GBK");
	    } catch (IllegalArgumentException e) {
	        System.out.println(e.getMessage());
	    }
	    return "";
	}

	public String getAddress(String ip){
		String country = getCountry(ip).equals(" CZ88.NET")?"":getCountry(ip);
		String area = getArea(ip).equals(" CZ88.NET")?"":getArea(ip);
        String address = country+" "+area;
		return address.trim();
	}
} 



 
