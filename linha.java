
package ai_trabalho2;
/*				PLAYER ID
 * 					- COMPUTER -> 0		O
 * 					- PLAYER   -> 1  	X
 * 
 * 
 * 
 */

import java.util.Scanner;

class DEPTH_LIMIT{
	public static int max;
}

class Node{
	int[][] board;
	int depth;
	Node father;
	int utility;
	int colPlayed;
	int player;
	int value;
	
	Node(){
		board = new int[6][7];	
		depth = 0;
		father= null;
		colPlayed = -1;
		player = -1;
		value = 0;
		//Scanner in = new Scanner(System.in);
		
		for(int l=0 ; l<6 ; l++){
			 for(int c=0 ; c<7 ; c++){
				 board[l][c] = -1;
			 }
		 }
	}
	Node(Node n){
		board = new int[6][7];	
		depth  = n.depth++;
		father = n;
		for(int l=0 ; l<6 ; l++){
			 for(int c=0 ; c<7 ; c++){
				 board[l][c] = n.board[l][c];
			 }
		 }
	}

	
}

class FBoards{
	
	public static void RenderBoard( Node state, char player){
		System.out.println("        0   1   2   3   4   5   6 ");  
		for(int l = 0 ; l < 6 ; l++ ){
			System.out.print("     ");
			for(int c = 0 ; c < 7 ; c++ ){
				if(state.board[l][c] == 1)
					System.out.print(" | " + player);
				else if (state.board[l][c] == 0)
					System.out.print(" | " + ReverseChar(player));
				else
					System.out.print(" | .");
			}
			
			System.out.println(" |");
		}
		
		
	}
	
	public static char ReverseChar(char c){
		
		if( c == 'X') return 'O';
		else return 'X';
		
	}
	
	public static boolean CompareFour(int a, int b,int c,int d) {
		if( a == b && a == c && a == d && a != -1) 
			return true;	
		else
			return false;
		
	}
	
	public static int CompareThree(int a,int b,int c,int d) {
		if( (a == b && a == c && d == -1 && c != -1)  || (b == c && b == d && a == -1 && c != -1)) {
			if( b == 1 )
				return 50;
			else
				return -50;
		}else
			return 0;
	}
	
	public static int CompareTwo(int a,int b,int c,int d) {
		if(	( a == b && c == d  && d == -1 && b != -1 ) ||
			( b == c && a == d  && d == -1 && b != -1 ) ){
			if( b == 1 )
				return 10;
			else
				return -10;
		}
				
		if( c == d && b == a  && a == -1 && c != -1 ){
			if( c == 1 )
				return 10;
			else
				return -10;
		}else
			return 0;
			
			
	}
	
	public static int CompareOne(int a,int b,int c,int d) {
		if( b == c && c == d && b == -1 && a != -1 ) return (a == 1) ? 1 : -1;

		if( a == c && c == d && c == -1 && b != -1 ) return (b == 1) ? 1 : -1;
		
		if( b == a && a == d && d == -1 && c != -1 ) return (c == 1) ? 1 : -1;
		
		
		if ( b == c && c == a && a == -1 && d != -1 ) return (d == 1) ? 1 : -1;	
			else return 0;
	}
	
	public static boolean IsFinal(Node state){
		for(int l=0; l<6; l++) {
			for(int c=0; c<4; c++) {
					if( CompareFour(state.board[l][c],state.board[l][c+1],state.board[l][c+2],state.board[l][c+3])) 
						return true;
				
			}
		}
		// Check Vertical
		for(int l=0; l<3; l++) {
			for(int c=0; c<7; c++) {
				if( CompareFour(state.board[l][c],state.board[l+1][c],state.board[l+2][c],state.board[l+3][c])) 
					return true;
			}
		}
		
		// Check Diagonal
		for(int l = 0 ; l < 3 ; l++) {
			for(int c = l+1 ; c < 4 ; c++) {
				if( CompareFour(state.board[l][c],state.board[l+1][c+1],state.board[l+2][c+2],state.board[l+3][c+3])) 
					
					return true;
			}	
		}
		
		for(int l = 0 ; l < 3 ; l++) {
			for(int c = 0 ; c <= l ; c++) {
				if( CompareFour(state.board[l][c],state.board[l+1][c+1],state.board[l+2][c+2],state.board[l+3][c+3])) 
					return true;
			}	
		}
		
		
		for(int l = 0 ; l < 3 ; l++) {
			for(int c = 3 ; c <= 5 - l ; c++) {
				if( CompareFour(state.board[l][c],state.board[l+1][c-1],state.board[l+2][c-2],state.board[l+3][c-3])) 
					return true;
					
			}	
		}
		for(int l = 0 ; l < 3 ; l++) {
			for(int c = 6-l ; c <= 6 ; c++) {
				if( CompareFour(state.board[l][c],state.board[l+1][c-1],state.board[l+2][c-2],state.board[l+3][c-3])) 
					return true;
			}	
		}

		return false;
			
	}
	
	public static boolean IsDraw(Node state){
		
		for(int l=0 ; l<6 ; l++){
			 for(int c=0 ; c<7 ; c++){
				 if( state.board[l][c] == -1) return false;
			 }
	    }
	    return true;
	}
	
	public static Node[] GetDescendants(Node state,int playerID) {
		
		Node[] desc = new Node[7];

		int nPos;
		
		for(int c=0; c < 7 ; c++) {
			nPos = -1;
			desc[c] = new Node(state);
			for(int l=0;  l<6 ; l++) {
				if( state.board[l][c] != -1)
					break;
				nPos++;				
			}
			desc[c].colPlayed=c;
			if(nPos != -1) 
				desc[c].board[nPos][c] = playerID;
			else
				desc[c] = null;
		}
		
		return desc;
	}
	
	public static int Utility(Node state,int player) {
		if( IsFinal(state) ) {
			if( player == 1) 
				return 512;
			else
				return -512;
			
		}else if( IsDraw(state) ){
			return 0;
		}
		
		
		int k;
		int sum=0;
		
		//Check horizontal
		for(int l=0; l<6; l++) {
			for(int c=0; c<4; c++) {
				if((k = CompareThree(state.board[l][c],state.board[l][c+1],state.board[l][c+2],state.board[l][c+3])) != 0) {
					sum += k;
					
				}else if((k =  CompareTwo( state.board[l][c],state.board[l][c+1],state.board[l][c+2],state.board[l][c+3])) != 0){
						sum += k;
					
				}else if((k =  CompareOne( state.board[l][c],state.board[l][c+1],state.board[l][c+2],state.board[l][c+3])) != 0){
						sum += k;
				}
			}
		}
		
		// Check Vertical
		for(int l=0; l<3; l++) {
			for(int c=0; c<7; c++) {
				if((k= CompareThree(state.board[l][c],state.board[l+1][c],state.board[l+2][c],state.board[l+3][c])) != 0) {
					sum += k;
					
				}else if((k= CompareTwo(state.board[l][c],state.board[l+1][c],state.board[l+2][c],state.board[l+3][c])) != 0) {
							sum += k;
					
				}else if((k= CompareOne(state.board[l][c],state.board[l+1][c],state.board[l+2][c],state.board[l+3][c])) != 0) {
						sum += k;
				}
			}
		}
		
		// Check Diagonal
		for(int l = 0 ; l < 3 ; l++) {
			for(int c = l+1 ; c < 4 ; c++) {
				if((k= CompareThree(state.board[l][c],state.board[l+1][c+1],state.board[l+2][c+2],state.board[l+3][c+3])) != 0) {
					sum += k;
					
				}else if((k= CompareTwo(state.board[l][c],state.board[l+1][c+1],state.board[l+2][c+2],state.board[l+3][c+3])) != 0) {
						sum += k;
					
				}else if((k= CompareOne(state.board[l][c],state.board[l+1][c+1],state.board[l+2][c+2],state.board[l+3][c+3])) != 0) {
						sum += k;
				}
			}	
		}
		
		for(int l = 0 ; l < 3 ; l++) {
			for(int c = 0 ; c <= l ; c++) {
				if((k= CompareThree(state.board[l][c],state.board[l+1][c+1],state.board[l+2][c+2],state.board[l+3][c+3])) != 0) {
					sum += k;
					
				}else if((k= CompareTwo(state.board[l][c],state.board[l+1][c+1],state.board[l+2][c+2],state.board[l+3][c+3])) != 0) {
						sum += k;
					
				}else if((k= CompareOne(state.board[l][c],state.board[l+1][c+1],state.board[l+2][c+2],state.board[l+3][c+3])) != 0) {
						sum += k;
				}	
			}	
		}
		
		
		for(int l = 0 ; l < 3 ; l++) {
			for(int c = 3 ; c <= 5 - l ; c++) {
				if((k= CompareThree(state.board[l][c],state.board[l+1][c-1],state.board[l+2][c-2],state.board[l+3][c-3])) != 0) {
					sum += k;
					
				}else if((k= CompareTwo(state.board[l][c],state.board[l+1][c-1],state.board[l+2][c-2],state.board[l+3][c-3])) != 0) {
						sum += k;
					
				}else if((k= CompareOne(state.board[l][c],state.board[l+1][c-1],state.board[l+2][c-2],state.board[l+3][c-3])) != 0) {
						sum += k;
				}	
					
			}	
		}
		for(int l = 0 ; l < 3 ; l++) {
			for(int c = 6-l ; c <= 6 ; c++) {
				if((k= CompareThree(state.board[l][c],state.board[l+1][c-1],state.board[l+2][c-2],state.board[l+3][c-3])) != 0) {
					sum += k;
					
				}else if((k= CompareTwo(state.board[l][c],state.board[l+1][c-1],state.board[l+2][c-2],state.board[l+3][c-3])) != 0) {
						sum += k;
					
				}else if((k= CompareOne(state.board[l][c],state.board[l+1][c-1],state.board[l+2][c-2],state.board[l+3][c-3])) != 0) {
						sum += k;
				}	
					
			}	
		}
		return -sum;
	}
	
	public static int Min(int a,int b) {
		if( a < b)
			return a;
		else
			return b;
		
	}
	
	public static int Max(int a,int b) {
		if( a > b)
			return a;
		else
			return b;
		
	}

	public static boolean DropCoin(Node state,int col,int player) {
			
		if(state.board[0][col] != -1)
			return false;
		
		for(int l=1 ; l<6 ; l++) {
			if(state.board[l][col] != -1){
				state.board[l-1][col] = player;
				break;
			}else if(l == 5){
				state.board[l][col] = player;
			}
			
		}
		return true;
		
	}

	public static void ChangePlayer(Node state) {
		if(state.player == 1)
			state.player = 0;
		else
			state.player = 1;
		
	}

	public static int MinMax(Node state) {
		int v;

		int maxP=0;
		int max=Integer.MIN_VALUE;
		state.depth=0;
		Node[] succ = GetDescendants(state,0);
		
		for(int c=0; c<7 ; c++) {
			if(succ[c] != null){
				v = Min_Value(succ[c]);
				System.out.println("=> Successor for column played n " + succ[c].colPlayed + " has value " + v);
				System.out.println("=> Utility value = " + Utility(succ[c],0));
				if(v > max) {
					max = v;
					maxP = c;
				}
			}
		}
		
		return maxP;
		
	}
	
	public static int Max_Value(Node state) {
		
		if(IsFinal(state) || state.depth >= DEPTH_LIMIT.max){
			return Utility(state,0);
		}
		
		Node[] succ = GetDescendants(state,0);
		
		int v = Integer.MIN_VALUE;
		for(int c = 0 ; c < 7 ; c++) { 			
			if(succ[c] != null){
				v = Math.max(v, Min_Value(succ[c]) ) ;
			}
		}
		return v;
	}

	public static int Min_Value(Node state) {

		if(IsFinal(state) || state.depth >= DEPTH_LIMIT.max){
			return Utility(state,1);
		}
		
		Node[] succ = GetDescendants(state,1);
		
		int v = Integer.MAX_VALUE;;
		for(int c = 0 ; c < 7 ; c++) { 			
			if(succ[c] != null)	{
				v = Math.min(v, Max_Value(succ[c]) ) ;
			}
		}
		return v;
	}

	public static int MinMaxAlphaBeta(Node state) {
		int v;

		int maxP=0;
		int max=Integer.MIN_VALUE;
		state.depth=0;
		Node[] succ = GetDescendants(state,0);
		
		for(int c=0; c<7 ; c++) {
			if(succ[c] != null){
				v = Min_ValueAlphaBeta(succ[c],Integer.MIN_VALUE,Integer.MAX_VALUE);
				//System.out.println("=> Successor for column played n " + succ[c].colPlayed + " has value " + v);
				//System.out.println("=> Utility value = " + Utility(succ[c],0));
				if(v > max) {
					max = v;
					maxP = c;
				}
			}
		}
		
		return maxP;
		
	}
	
	public static int Max_ValueAlphaBeta(Node state,int alpha,int beta) {
		
		if(IsFinal(state) || state.depth >= DEPTH_LIMIT.max){
			return Utility(state,0);
		}
		
		Node[] succ = GetDescendants(state,0);
		
		int v = Integer.MIN_VALUE;
		for(int c = 0 ; c < 7 ; c++) { 			
			if(succ[c] != null){
				v = Math.max(v, Min_ValueAlphaBeta(succ[c],alpha,beta) ) ;
				if (v >= beta) {
					return v;
				}
				alpha = Math.max(alpha, v);
			}
		}
		return v;
	}

	public static int Min_ValueAlphaBeta(Node state,int alpha,int beta) {

		if(IsFinal(state) || state.depth >= DEPTH_LIMIT.max){
			return Utility(state,1);
		}
		
		Node[] succ = GetDescendants(state,1);
		
		int v = Integer.MAX_VALUE;;
		for(int c = 0 ; c < 7 ; c++) { 			
			if(succ[c] != null)	{
				v = Math.min(v, Max_ValueAlphaBeta(succ[c],alpha,beta) ) ;
				if (v <= alpha) {
					return v;
				}
				beta = Math.min(v, beta);
			}
		}
		return v;
	}

}




public class linha {
	/*				PLAYER ID
	 * 					- COMPUTER -> 0		O
	 * 					- PLAYER   -> 1  	X
	 */
	public static void main (String[] args) {
		Node state = new Node();
		int col,bMove;
		int cPlayer=-1;
		char car;
		Scanner in = new Scanner(System.in);
		
		while(cPlayer != 0 && cPlayer != 1) {
			System.out.println("Do you want to be: ");
			System.out.println("	0) O");
			System.out.println("	1) X");
			cPlayer=in.nextInt();
		}
		if(cPlayer == 0)
			car = 'O';
		else
			car = 'X';
		int alg=-1;
		while(alg != 0 && alg != 1) {
			System.out.println("\nSelect your search algorithm: ");
			System.out.println("	0) Minmax");
			System.out.println("	1) Minmax with AlphaBeta cut");
			alg=in.nextInt();
		}
		while(state.player != 0 && state.player != 1) {
			System.out.println("\nDo you wish to go first?");
			System.out.println("	0) No");
			System.out.println("	1) Yes");
			state.player=in.nextInt();
		}

		System.out.print("\nSelect depth limit: ");
		DEPTH_LIMIT.max=in.nextInt();
	
		while(!(FBoards.IsFinal(state))) {
			FBoards.RenderBoard(state, car);
			if(state.player == 1) {
				System.out.print("\nHuman plays (Select a number between 0 and 6 representing the column): ");
				col = in.nextInt();
				
				if( col < 0 || col > 6) {
					System.out.println("\n INVALID COLUMN NUMBER ( Column must be between 0 and 6 ) \n");
				}else if(!FBoards.DropCoin(state, col, 1)) {
					System.out.println("\n INVALID COLUMN NUMBER ( Column already full ) \n");
				}else {
					FBoards.ChangePlayer(state);
				}
				
			}else {
				if(alg == 0 )
					bMove = FBoards.MinMax(state);
				else
					bMove = FBoards.MinMaxAlphaBeta(state);
				
				FBoards.DropCoin(state, bMove, 0);
				FBoards.ChangePlayer(state);
				System.out.println("\nComputer plays in column: " + bMove);
			}
			
		}
		FBoards.RenderBoard(state, car);
	    System.out.println();
	    if(state.player == 0)
	    	System.out.println("    -> You win!!!");
	    	else
		    	System.out.println("    -> You lose! Computer wins.");
	
		
	}
}
