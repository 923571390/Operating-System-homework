import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Disc implements Serializable{
	public static final int DIR_LEN = 32;
	private char[] FAT = new char[128];
	private char[][] disc = new char[128][512];
	private int nowBl;
	private int nowNum;

	public Disc() {
		for (int i = 0; i < 128; ++i) {
			FAT[i] = 0;
		}
		FAT[0] = 2;
		for (int j = 0; j < 512; j++) {
			disc[0][j] = ' ';
		}
		disc[0][0] = '.';
		disc[0][16] = 0;
		disc[0][18] = 0;
		disc[0][32] = '.';
		disc[0][33] = '.';
		disc[0][48] = 2;
	}
	
	public void reload(ArrayList CurDirs, ArrayList CurFiles)
	{
		CurDirs.clear();
		CurFiles.clear();
		for(int i = 2; i < 16; i++)
		{
			if(disc[0][i * 32] != ' ')
			{
				if(disc[0][i * 32 + 16] == 0)
				{
					CurDirs.add(getDirectoryName(0, i));		
				}
				else if(disc[0][i * 32 + 16] == 1)
				{
					CurFiles.add(getDirectoryName(0, i));
				}
			}
		}
	}

	public char[] getDirectory(int CurBlock, int Num) {
		char[] Dir = new char[DIR_LEN];
		for (int i = 0; i < DIR_LEN; ++i) {
			Dir[i] = disc[CurBlock][Num * DIR_LEN + i];
		}
		return Dir;
	}

	public void findDirLoc(int CurBlock, String Name) {
		int i;
		for (i = 0; i < 16; i++) {
			if (Name.equals(getDirectoryName(CurBlock, i))) {
				break;
			}
		}
		nowBl = CurBlock;
		nowNum = i;
	}

	public String getDirectoryName(int CurBlock, int Num) {
		char[] Directory = getDirectory(CurBlock, Num);
		String Name = "";
		for (int i = 0; i < 16; ++i) {
			Name += Directory[i];
		}
		return Name.trim();
	}

	public String getFileContent(int CurBlock, String FileName) {
		int i;
		for (i = 0; i < 16; i++) {
			if (FileName.equals(getDirectoryName(CurBlock, i))
				&& disc[CurBlock][i * DIR_LEN + 16] == 1) {
				break;
			}
		}
		if(i != 16)
		{
		int TxtBloNum = (int) disc[CurBlock][i * DIR_LEN + 18];
		return getTextByBlock(TxtBloNum);
		}
		return "";
	}

	public String getTextByBlock(int BloNum) {
		String Text = "";
		do {
			for (int i = 0; i < 256; ++i) {
				Text += disc[BloNum][i];
			}
			BloNum = FAT[BloNum];
		} while (BloNum != 1000);
		return Text.trim();
	}

	public int enterDirectory(
		int CurBlock,
		String FileName,
		ArrayList CurDirs,
		ArrayList CurFiles) {
		findDirLoc(CurBlock, FileName);
		int newCurBlock = disc[nowBl][nowNum * 32 + 18];
		CurDirs.clear();
		CurFiles.clear();
		int i;
		int offset = 64;
		for (i = 2; i < 16; i++) {
			char[] Dir = getDirectory(newCurBlock, i);
			String name = getDirectoryName(newCurBlock, i);
			if (name != "" && disc[newCurBlock][offset + 16] == 0) {
				CurDirs.add(name);
			} else if (name != "" && disc[newCurBlock][offset + 16] == 1) {
				CurFiles.add(name);
			}
			offset += 32;
		}
		return newCurBlock;
	}

	public int exitDirectory(
		int CurBlock,
		ArrayList CurDirs,
		ArrayList CurFiles) {
		if (disc[CurBlock][48] == 0) {
			int newCurBlock = disc[CurBlock][50];
			CurDirs.clear();
			CurFiles.clear();
			int i;
			int offset = 64;
			for (i = 2; i < 16; i++) {
				char[] Dir = getDirectory(newCurBlock, i);
				String name = getDirectoryName(newCurBlock, i);
				if (disc[newCurBlock][offset + 16] == 0) {
					CurDirs.add(name);
				} else if (disc[newCurBlock][offset + 16] == 1) {
					CurFiles.add(name);
				}
				offset += 32;
			}
			return newCurBlock;
		} else
			return -1;
	}

	public boolean saveFileContent(
		int CurBlock,
		String FileName,
		String Text) {
		int i;
		for (i = 0; i < 16; i++) {
			if (FileName.equals(getDirectoryName(CurBlock, i))) {
				break;
			}
		}
		disc[CurBlock][i * DIR_LEN + 17] = (char)Text.length();
		int TxtBloNum = (int) disc[CurBlock][i * DIR_LEN + 18];
		delTailBlocks(TxtBloNum);
		for (int j = 0; j < Text.length(); j++) {
			disc[TxtBloNum][j] = Text.charAt(j);
		}
		return true;
	}

	public void delTailBlocks(int BlockNum) {
		if (FAT[BlockNum] != 0 && FAT[BlockNum] != 1000) {
			delBlocks((int) FAT[BlockNum]);
		}
	}

	public void delBlocks(int BlockNum) {
		if (FAT[BlockNum] != 0 && FAT[BlockNum] != 1000) {
			delBlocks((int) FAT[BlockNum]);
		}
		FAT[BlockNum] = 0;
	}

	public boolean addFile(int CurBlock, String Name) {
		int i;
		for (i = 0; i < 16; i++) {
			if (disc[CurBlock][i * DIR_LEN] == ' ') {
				break;
			}
		}
		if (i != 16) {
			int offset = i * DIR_LEN;
			for (int j = 0; j < Name.length(); j++) {
				disc[CurBlock][offset + j] = Name.charAt(j);
			}
			disc[CurBlock][offset + 16] = 1;
			disc[CurBlock][offset + 17] = 0;
			disc[CurBlock][offset + 18] = (char) assignBlock();
			return true;
		} 
		else return false;
	}

	public boolean addDirectory(int CurBlock, String Name) {
		int freeBlock = assignBlock();
		if (freeBlock == -1) {
			return false;
		}
		int i;
		for (i = 0; i < 16; i++) {
			if (disc[CurBlock][i * DIR_LEN] == ' ') {
				break;
			}
		}
		if (i != 16) {
			int offset = i * DIR_LEN;
			for (int j = 0; j < Name.length(); j++) {
				disc[CurBlock][offset + j] = Name.charAt(j);
			}
			disc[CurBlock][offset + 16] = 0;
			disc[CurBlock][offset + 17] = 0;
			disc[CurBlock][offset + 18] = (char) freeBlock;
			initialDirectoryBlock(freeBlock, CurBlock);
			return true;
		} 
		else return false;
	}

	private void initialDirectoryBlock(int CurBlock, int ParentBlock) {
		disc[CurBlock][0] = '.';
		disc[CurBlock][16] = 0;
		disc[CurBlock][18] = (char) CurBlock;
		disc[CurBlock][32] = '.';
		disc[CurBlock][33] = '.';
		disc[CurBlock][48] = 0;
		disc[CurBlock][50] = (char) ParentBlock;
	}

	public int assignBlock() {
		for (int i = 0; i < 128; i++) {
			if (FAT[i] == 0) {
				FAT[i] = 1000;
				for (int j = 0; j < 512; j++) {
					disc[i][j] = ' ';
				}
				return i;
			}
		}
		return -1;
	}

	public void delDirectory(int CurBlock, String DirName) {
		findDirLoc(CurBlock, DirName);
		int Num = nowNum;
		int Block = disc[nowBl][nowNum * 32 + 18];
		delDirectory(Block);
		clearDirectory(CurBlock, Num);
	}

	private void delDirectory(int block) {
		for (int i = 2; i < 16; ++i) {
			if (disc[block][i * 32] == ' ') {
				continue;
			}
			if (disc[block][i * 32 + 16] == 1) {
				delFile(block, i);
			}
			if (disc[block][i * 32 + 16] == 0) {
				int a = disc[block][i * 32 + 18];
				delDirectory(a);
			}
		}
		FAT[block] = 0;
	}

	public void delFile(int CurBlock, String name) {
		findDirLoc(CurBlock, name);
		delFile(nowBl, nowNum);
	}

	public void delFile(int Block, int num) {
		delBlocks((int) disc[Block][num * 32 + 18]);
		clearDirectory(Block, num);
	}

	public void clearDirectory(int CurBlock, int nownum) {
		for (int i = 0; i < 31; i++) {
			disc[CurBlock][nownum * 32 + i] = ' ';
		}
	}
	
	public int getSize(int CurBlock, String FileName)
	{
		findDirLoc(CurBlock, FileName);
		return (int)disc[CurBlock][nowNum * 32 + 17];
	}
	
	public int getLocation(int CurBlock, String FileName)
	{
		findDirLoc(CurBlock, FileName);
		return (int)(disc[nowBl][nowNum * 32 + 18] * 256);
	}
}
