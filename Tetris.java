
enum TetrisState {
	Running(0), NewBlock(1), Finished(2);
	private final int value;

	private TetrisState(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}

public class Tetris {
	private static Matrix[][] blkArray;
	private static int iScreenDw;
	private static Matrix[][] setOfBlockObjects;
	private static Matrix[][] createSetOfBlocks(int[][][][] setOfArrays) throws Exception {
		Matrix setOfBlocks[][] = new Matrix[setOfArrays.length][setOfArrays[0].length];

		for (int x = 0; x < setOfArrays.length; x++) {
			for (int y = 0; y < setOfArrays[0].length; y++) {
				setOfBlocks[x][y] = new Matrix(setOfArrays[x][y]);
			}
		}

		return setOfBlocks;
	}
	public static void init(int[][][][] setOfBlockArrays) throws Exception {
		iScreenDw = setOfBlockArrays[0][0].length; // cause I made all of block
													// 4x4, I only need to check
													// [0][0].length
		blkArray = createSetOfBlocks(setOfBlockArrays);
	}
	private int iScreenDy;
	private int iScreenDx;
	private TetrisState state = TetrisState.NewBlock;
	private boolean isJustStarted = true;
	private int top;
	private int left;
	private Matrix iScreen;
	private Matrix oScreen;
	private Matrix currBlk;
	private int idxBlockType;
	private int idxBlockDegree;

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

	public void printScreen() {
		Matrix screen = oScreen;
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

	public static void printMatrix(Matrix blk) {

	}

	public static Matrix deleteFullLines(Matrix screen, Matrix blk, int top, int dy) throws Exception {
		int numOfLinesToCheck = blk.get_dy(), nIndex, numOfDeletedLine = 0, scope;
		Matrix lineToCheck, tmpBlk;
		if (top + blk.get_dy() > dy) {
			numOfLinesToCheck = numOfLinesToCheck - (top + blk.get_dy() - dy);
		}
		for (nIndex = numOfLinesToCheck - 1; nIndex >= 0; nIndex--) {
			scope = top + nIndex + numOfDeletedLine;
			lineToCheck = screen.clip(scope, 0, scope + 1, screen.get_dx());
			if (lineToCheck.sum() == screen.get_dx()) {
				tmpBlk = screen.clip(0, 0, scope, screen.get_dx());
				screen.paste(tmpBlk, 1, 0);
				numOfDeletedLine++;
			}
		}
		return screen;
	}

	public Tetris(int cy, int cx) throws MatrixException {
		top = 0;
		iScreenDy = cy;
		iScreenDx = cx;
		left = iScreenDw + iScreenDx / 2 - 2;
		int[][] arrayScreen = createArrayScreen(iScreenDy, iScreenDx, iScreenDw);
		iScreen = new Matrix(arrayScreen);
		oScreen = new Matrix(arrayScreen);
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

	public TetrisState accept(char key) throws Exception {
		Matrix tempBlk;

		if (state == TetrisState.NewBlock) {
			if (isJustStarted == false){
				oScreen = deleteFullLines(oScreen, currBlk, top, iScreenDy); /* homework1 v11 */
			}
			isJustStarted = false;
			iScreen = new Matrix(oScreen);
			idxBlockType = Integer.parseInt(Character.toString(key));
			idxBlockDegree = 0;
			currBlk = blkArray[idxBlockType][idxBlockDegree];
			state = TetrisState.Running;
			top = 0;
			left = iScreenDw + iScreenDx/2 - 2;
			tempBlk = iScreen.clip(top, left, top + currBlk.get_dy(), left + currBlk.get_dx());
			tempBlk = tempBlk.add(currBlk);

			if (tempBlk.anyGreaterThan(1)) {
				System.out.println("Game Over!");
				state = TetrisState.Finished;
				return state;
			}
			oScreen = new Matrix(iScreen); // ?
			oScreen.paste(tempBlk, top, left); // ?
			return state;
		}
		
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
		case 'w': /* homework1 v9 */
			idxBlockDegree = (idxBlockDegree + 1) % 4;
			currBlk = blkArray[idxBlockType][idxBlockDegree];
			break;
		case ' ': /* homework1 v10 */
			 do {
				top++;
				tempBlk = iScreen.clip(top, left, top + currBlk.get_dy(), left + currBlk.get_dx());
				tempBlk = tempBlk.add(currBlk);
			} while (tempBlk.anyGreaterThan(1) != true);
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
				state = TetrisState.NewBlock;
				break;
			case 'w': /* homework1 v9 */
				idxBlockDegree = (idxBlockDegree + 3) % 4;
				currBlk = setOfBlockObjects[idxBlockType][idxBlockDegree];
				break;
			case ' ':
				top--;
				state = TetrisState.NewBlock; /* homework1 v10 */
				break;
			}
			tempBlk = iScreen.clip(top, left, top + currBlk.get_dy(), left + currBlk.get_dx());
			tempBlk = tempBlk.add(currBlk);
		}
		oScreen = new Matrix(iScreen);
		oScreen.paste(tempBlk, top, left);
		return state;
	}
}

class TetrisException extends Exception {
	public TetrisException() {
		super("Tetris Exception");
	}

	public TetrisException(String msg) {
		super(msg);
	}
}
