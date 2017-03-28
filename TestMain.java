
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class TestMain {
	private static int iScreenDy = 15;
	private static int iScreenDx = 10;
	private static int iScreenDw = 4;

	private static int[][] createArrayScreen(int dy, int dx, int dw) {
		int y, x;
		int[][] array = new int[dy + dw][dx + 2 * dw];
		for (y = 0; y < array.length; y++)
			for (x = 0; x < dw; x++)
				array[y][x] = 1;
		for (y = 0; y < array.length; y++)
			for (x = dw + dx; x < array[0].length; x++)
				array[y][x] = 1;
		for (y = dy; y < array.length; y++)
			for (x = 0; x < array[0].length; x++)
				array[y][x] = 1;
		return array;
	}
	
	private static Matrix[][] createSetOfBlocks(int[][][][] setOfArrays) throws Exception {
		Matrix setOfBlocks[][] = new Matrix[setOfArrays.length][setOfArrays[0].length];
		
		for( int x = 0; x < setOfArrays.length; x++) {
			for (int y = 0; y < setOfArrays[0].length; y++) {
				setOfBlocks[x][y] = new Matrix(setOfArrays[x][y]);
			}
		}
		
		return setOfBlocks;
	}

	public static void printScreen(Matrix screen) {
		int dy = screen.get_dy();
		int dx = screen.get_dx();
		int dw = iScreenDw;

		int array[][] = screen.get_array();
		for (int y = 0; y < dy - dw + 1; y++) {
			for (int x = dw - 1; x < dx - dw + 1; x++) {
				if (array[y][x] == 0)
					System.out.print("бр ");
				else if (array[y][x] == 1)
					System.out.print("бс ");
				else
					System.out.print("x ");
			}
			System.out.println();
		}
	}

	/* homework1 v8 start */
	private static int nBlockTypes;
	private static int nBlockDegrees;
	private static Matrix[][] setOfBlockObjects;
	private static int[][][][] setOfBlockArrays = {
			{ { { 1, 1, 1, 1 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 } },
					{ { 1, 1, 1, 1 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 } } },
			{ { { 0, 1, 0, 0 }, { 1, 1, 1, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 1, 0, 0 }, { 1, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 1, 1, 1, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 1, 0, 0, 0 }, { 1, 1, 0, 0 }, { 1, 0, 0, 0 }, { 0, 0, 0, 0 } } },
			{ { { 1, 1, 0, 0 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 1, 1, 0, 0 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 1, 1, 0, 0 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 1, 1, 0, 0 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } } },
			{ { { 0, 1, 0, 0 }, { 0, 1, 0, 0 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 1, 0, 0, 0 }, { 1, 1, 1, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 1, 1, 0, 0 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 1, 1, 1, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } } },
			{ { { 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 1, 1, 1, 0 }, { 1, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 1, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 0, 1, 0 }, { 1, 1, 1, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } } },
			{ { { 1, 1, 0, 0 }, { 0, 1, 1, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 1, 0, 0 }, { 1, 1, 0, 0 }, { 1, 0, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 1, 1, 0, 0 }, { 0, 1, 1, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 1, 0, 0 }, { 1, 1, 0, 0 }, { 1, 0, 0, 0 }, { 0, 0, 0, 0 } } },
			{ { { 0, 1, 1, 0 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 1, 0, 0, 0 }, { 1, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 1, 1, 0 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 1, 0, 0, 0 }, { 1, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 0 } } } 
	};
	/* homework1 v8 end */

	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static String line = null;
	private static int nKeys = 0;

	private static char getKey() throws IOException {
		char ch;
		if (nKeys != 0) {
			ch = line.charAt(line.length() - nKeys);
			nKeys--;
			return ch;
		}

		do {
			line = br.readLine();
			nKeys = line.length();
		} while (nKeys == 0);
		ch = line.charAt(0);
		nKeys--;
		return ch;
	}
	
	/* homework1 v11 start */
	public static Matrix deleteFullLines(Matrix screen, Matrix blk, int top, int dy) throws Exception {
		int numOfLinesToCheck = blk.get_dy(), nIndex, numOfDeletedLine = 0, scope;
		Matrix lineToCheck, tmpBlk;
		if (top + blk.get_dy() > dy) {
			numOfLinesToCheck = numOfLinesToCheck - (top + blk.get_dy() - dy);
		}
		for ( nIndex = numOfLinesToCheck - 1; nIndex >= 0 ; nIndex-- )
		{
			scope = top + nIndex + numOfDeletedLine;
			lineToCheck = screen.clip(scope, 0, scope+1, screen.get_dx());
			if ( lineToCheck.sum() == screen.get_dx()) {
				tmpBlk = screen.clip(0 , 0, scope, screen.get_dx());
				screen.paste(tmpBlk, 1, 0);
				numOfDeletedLine++;
			}
		}
		return screen;
	}
	/* homework1 v11 end */
	
	public static void main(String[] args) throws Exception {
		boolean newBlockNeeded = false;
		int top = 0;
		int left = iScreenDw + iScreenDx / 2 - 2;
		int[][] arrayScreen = createArrayScreen(iScreenDy, iScreenDx, iScreenDw);
		char key;
		Matrix iScreen = new Matrix(arrayScreen);
		nBlockTypes = setOfBlockArrays.length;
		nBlockDegrees = setOfBlockArrays[0].length;
		setOfBlockObjects = createSetOfBlocks(setOfBlockArrays);
		
		/* homework1 v8 start*/
		Random random = new Random();
		int idxBlockType = random.nextInt(nBlockTypes);
		int idxBlockDegree = 0;
		Matrix currBlk = setOfBlockObjects[idxBlockType][idxBlockDegree];
		/* homework1 v8 end */
	
		
		Matrix tempBlk = iScreen.clip(top, left, top + currBlk.get_dy(), left + currBlk.get_dx());

		

		tempBlk = tempBlk.add(currBlk);
		Matrix oScreen = new Matrix(iScreen);
		oScreen.paste(tempBlk, top, left);
		printScreen(oScreen);
		System.out.println();

		

		while ((key = getKey()) != 'q') {
			switch (key) {
			case 'a':
				left--;
				break;
			case 'd':
				left++;
				break;
			case 's':
				top++;
				break;
			case 'w':																			/* homework1 v9 */
				idxBlockDegree = (idxBlockDegree + 1) % 4;
				currBlk = setOfBlockObjects[idxBlockType][idxBlockDegree];
				break;
			case ' ':																			/* homework1 v10 */
				while(tempBlk.anyGreaterThan(1) != true) {
					top++;
					tempBlk = iScreen.clip(top, left, top + currBlk.get_dy(), left + currBlk.get_dx());
					tempBlk = tempBlk.add(currBlk);
				}								// this case is related with the case' ' of if(tempBlk.anyGreaterThan(1))
				break;
			default:
				System.out.println("unknown key!");
			}
			tempBlk = iScreen.clip(top, left, top + currBlk.get_dy(), left + currBlk.get_dx());
			tempBlk = tempBlk.add(currBlk);

			if (tempBlk.anyGreaterThan(1)) {
				switch (key) {
				case 'a':
					left++;
					break;
				case 'd':
					left--;
					break;
				case 's':
					top--;
					newBlockNeeded = true;
					break;
				case 'w':																			/* homework1 v9 */
					idxBlockDegree = (idxBlockDegree + 3) % 4;
					currBlk = setOfBlockObjects[idxBlockType][idxBlockDegree];
					break;
				case ' ':																			
					top--;
					newBlockNeeded = true;												/* homework1 v10 */
					break;
				}
				tempBlk = iScreen.clip(top, left, top + currBlk.get_dy(), left + currBlk.get_dx());
				tempBlk = tempBlk.add(currBlk);
			}
			oScreen = new Matrix(iScreen);
			oScreen.paste(tempBlk, top, left);
			printScreen(oScreen);
			System.out.println();

			if (newBlockNeeded) {
				deleteFullLines(oScreen, currBlk, top, oScreen.get_dy()-iScreenDw);	/* homework1 v11 */
				iScreen = new Matrix(oScreen);
				top = 0;
				left = iScreenDw + iScreenDx / 2 - 2;
				newBlockNeeded = false;
//				currBlk = new Matrix(arrayBlk);
				idxBlockType = random.nextInt(nBlockTypes);
				currBlk = setOfBlockObjects[idxBlockType][idxBlockDegree];
				tempBlk = iScreen.clip(top, left, top + currBlk.get_dy(), left + currBlk.get_dx());
				tempBlk = tempBlk.add(currBlk);

				if (tempBlk.anyGreaterThan(1)) {
					System.out.println("Game Over!");
					System.exit(0);
				}
				oScreen = new Matrix(iScreen);
				oScreen.paste(tempBlk, top, left);
				printScreen(oScreen);
				System.out.println();
			}
		}
	}
}
