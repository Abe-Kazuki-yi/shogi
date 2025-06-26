package jp.furykasukabe.shogi.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.furykasukabe.shogi.bean.Board;
import jp.furykasukabe.shogi.bean.Piece;
import jp.furykasukabe.shogi.dto.MoveRequest;
import jp.furykasukabe.shogi.dto.Square;
import jp.furykasukabe.shogi.entity.PieceInfo;
import jp.furykasukabe.shogi.repository.PieceRepository;
import jp.furykasukabe.shogi.service.BoardService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	private final PieceRepository pieceRepository;

	@Override
	public List<Square> findMovableSquare(Board board, Square square) {
		List<Square> lists = null;
		try {
			if (board.getMyFormation()[square.getX()][square.getY()] == null)
				throw new RuntimeException();

			switch (board.getMyFormation()[square.getX()][square.getY()].getId()) {
			case 1:
				lists = king(board, square);
				break;
			case 2:
				lists = gold(board, square);
				break;
			case 3:
				if (board.getMyFormation()[square.getX()][square.getY()].isPromoted())
					lists = gold(board, square);
				else
					lists = silver(board, square);
				break;
			case 4:
				if (board.getMyFormation()[square.getX()][square.getY()].isPromoted())
					lists = gold(board, square);
				else
					lists = horse(board, square);
				break;
			case 5:
				if (board.getMyFormation()[square.getX()][square.getY()].isPromoted())
					lists = gold(board, square);
				else
					lists = lance(board, square);
				break;
			case 6:
				if (board.getMyFormation()[square.getX()][square.getY()].isPromoted())
					lists = dragon(board, square);
				else
					lists = rook(board, square);
				break;
			case 7:
				if (board.getMyFormation()[square.getX()][square.getY()].isPromoted())
					lists = pegasus(board, square);
				else
					lists = bishop(board, square);
				break;
			case 8:
				if (board.getMyFormation()[square.getX()][square.getY()].isPromoted())
					lists = gold(board, square);
				else
					lists = pawn(board, square);
				break;
			}
		} catch (RuntimeException e) {

		}
		return lists;
	}

	@Override
	public Board advanceOneStep(Board board, MoveRequest moveRequest) {
		Board nextBoard = board;
		Piece[][] currentMyFormation = board.getMyFormation();
		Piece[][] currentOpponentFormation = board.getOpponentFormation();

		int beforeX = moveRequest.getFrom()[0];
		int beforeY = moveRequest.getFrom()[1];
		int targetX = moveRequest.getTo()[0];
		int targetY = moveRequest.getTo()[1];

		if (currentMyFormation[beforeX][beforeY] != null) {
			currentMyFormation[targetX][targetY] = board.getMyFormation()[beforeX][beforeY];
			currentMyFormation[beforeX][beforeY] = null;

			nextBoard.setMyFormation(currentMyFormation);

			if (currentOpponentFormation[targetX][targetY] != null) {
				board.addMyHand(currentOpponentFormation[targetX][targetY]);
				currentOpponentFormation[targetX][targetY] = null;
				nextBoard.setOpponentFormation(currentOpponentFormation);
				nextBoard.setMyHand(board.getMyHand());
			}
		}
		return nextBoard;
	}

	@Override
	public boolean isPromotable(Board board, MoveRequest moveRequest) {
		if (!board.getMyFormation()[moveRequest.getFrom()[0]][moveRequest.getFrom()[1]].isPromoted()) {
			if (board.getMyFormation()[moveRequest.getFrom()[0]][moveRequest.getFrom()[1]].getPromotedName() != null) {
				if (moveRequest.getFrom()[1] <= 3 || moveRequest.getTo()[1] <= 3)
					return true;
			}
		}
		return false;
	}

	@Override
	public Board executePromote(Board board, Square square) {
		Piece[][] myFormation = board.getMyFormation();
		myFormation[square.getX()][square.getY()].setPromoted(true);
		board.setMyFormation(myFormation);
		return board;
	}

	
	@Override
	public List<Square> findDropableSquare(Board board){
		List<Square> squares = new ArrayList<>();
		for(int i = 1; i < 10; i++) {
			for(int j = 1; j < 10; j++) {
				if(board.getMyFormation()[i][j] == null && board.getOpponentFormation()[i][j] == null) squares.add(new Square(i,j));
			}
		}
		return  squares;
	}
	
	@Override
	public Board dropPiece(Board board, Piece piece, Square square) {
		PieceInfo pieceInfo = pieceRepository.findById(piece.getId());
		Piece rePiece = new Piece(pieceInfo);
		
		Piece[][] myFormation = board.getMyFormation();
		myFormation[square.getX()][square.getY()] = rePiece;
		board.removeMyHand(rePiece);
		board.setMyFormation(myFormation);
		return board;
	}
	
	private List<Square> king(Board board, Square square) {
		List<Square> lists = new ArrayList<>();
		for (int x = square.getX() - 1; x < square.getX() + 2; x++) {

			for (int y = square.getY() - 1; y < square.getY() + 2; y++) {
				if (x == 0 || y == 0)
					throw new RuntimeException();
				try {
					if (board.getMyFormation()[x][y] != null)
						throw new RuntimeException();
					lists.add(new Square(x, y));
				} catch (RuntimeException e) {

				}
			}
		}
		return lists;
	}

	private List<Square> gold(Board board, Square square) {
		List<Square> lists = new ArrayList<>();
		int x;
		int y;

		x = square.getX() - 1;
		y = square.getY() - 1;
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		x = square.getX();
		y = square.getY() - 1;
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		x = square.getX() + 1;
		y = square.getY() - 1;
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		x = square.getX() - 1;
		y = square.getY();
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		x = square.getX() + 1;
		y = square.getY();
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		x = square.getX();
		y = square.getY() + 1;
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		return lists;
	}

	private List<Square> silver(Board board, Square square) {
		List<Square> lists = new ArrayList<>();
		int x;
		int y;

		x = square.getX() - 1;
		y = square.getY() - 1;
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		x = square.getX();
		y = square.getY() - 1;
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		x = square.getX() + 1;
		y = square.getY() - 1;
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		x = square.getX() - 1;
		y = square.getY() + 1;
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		x = square.getX() + 1;
		y = square.getY() + 1;
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		return lists;
	}

	private List<Square> horse(Board board, Square square) {
		List<Square> lists = new ArrayList<>();
		int x;
		int y;

		x = square.getX() - 1;
		y = square.getY() - 2;
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		x = square.getX() + 1;
		y = square.getY() - 2;
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		return lists;
	}

	private List<Square> lance(Board board, Square square) {
		List<Square> lists = new ArrayList<>();
		int x;
		int y;
		boolean flag = false;

		x = square.getX();
		y = square.getY();

		while (flag == false) {
			y--;
			if (isExist(x, y) && board.getMyFormation()[x][y] == null) {
				lists.add(new Square(x, y));
				if (board.getOpponentFormation()[x][y] != null)
					flag = true;
			} else
				flag = true;
		}

		return lists;
	}

	private List<Square> rook(Board board, Square square) {
		List<Square> lists = new ArrayList<>();
		int x;
		int y;

		for (int i = 0; i < 4; i++) {
			boolean flag = false;

			x = square.getX();
			y = square.getY();

			while (flag == false) {
				switch (i) {
				case 0:
					y--;
					break;
				case 1:
					y++;
					break;
				case 2:
					x--;
					break;
				case 3:
					x++;
					break;
				}
				if (isExist(x, y) && board.getMyFormation()[x][y] == null) {
					lists.add(new Square(x, y));
					if (board.getOpponentFormation()[x][y] != null)
						flag = true;
				} else
					flag = true;
			}
		}
		return lists;

	}

	private List<Square> dragon(Board board, Square square) {
		List<Square> lists = new ArrayList<>();
		int x;
		int y;

		for (int i = 0; i < 4; i++) {
			boolean flag = false;

			x = square.getX();
			y = square.getY();

			while (flag == false) {
				switch (i) {
				case 0:
					y--;
					break;
				case 1:
					y++;
					break;
				case 2:
					x--;
					break;
				case 3:
					x++;
					break;
				}
				if (isExist(x, y) && board.getMyFormation()[x][y] == null) {
					lists.add(new Square(x, y));
					if (board.getOpponentFormation()[x][y] != null)
						flag = true;
				} else
					flag = true;
			}
		}

		x = square.getX() - 1;
		y = square.getY() - 1;
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		x = square.getX() + 1;
		y = square.getY() - 1;
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		x = square.getX() - 1;
		y = square.getY() + 1;
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		x = square.getX() + 1;
		y = square.getY() + 1;
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		return lists;

	}

	private List<Square> bishop(Board board, Square square) {
		List<Square> lists = new ArrayList<>();
		int x;
		int y;

		for (int i = 0; i < 4; i++) {
			boolean flag = false;

			x = square.getX();
			y = square.getY();

			while (flag == false) {
				switch (i) {
				case 0:
					x--;
					y--;
					break;
				case 1:
					x--;
					y++;
					break;
				case 2:
					x++;
					y--;
					break;
				case 3:
					x++;
					y++;
					break;
				}
				if (isExist(x, y) && board.getMyFormation()[x][y] == null) {
					lists.add(new Square(x, y));
					if (board.getOpponentFormation()[x][y] != null)
						flag = true;
				} else
					flag = true;
			}
		}
		return lists;

	}

	private List<Square> pegasus(Board board, Square square) {
		List<Square> lists = new ArrayList<>();
		int x;
		int y;

		for (int i = 0; i < 4; i++) {
			boolean flag = false;

			x = square.getX();
			y = square.getY();

			while (flag == false) {
				switch (i) {
				case 0:
					x--;
					y--;
					break;
				case 1:
					x--;
					y++;
					break;
				case 2:
					x++;
					y--;
					break;
				case 3:
					x++;
					y++;
					break;
				}
				if (isExist(x, y) && board.getMyFormation()[x][y] == null) {
					lists.add(new Square(x, y));
					if (board.getOpponentFormation()[x][y] != null)
						flag = true;
				} else
					flag = true;
			}
		}

		x = square.getX() - 1;
		y = square.getY();
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		x = square.getX() + 1;
		y = square.getY();
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		x = square.getX();
		y = square.getY() - 1;
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		x = square.getX();
		y = square.getY() + 1;
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		return lists;

	}

	private List<Square> pawn(Board board, Square square) {
		List<Square> lists = new ArrayList<>();
		int x;
		int y;

		x = square.getX();
		y = square.getY() - 1;
		if (isExist(x, y) && board.getMyFormation()[x][y] == null)
			lists.add(new Square(x, y));

		return lists;
	}

	private boolean isExist(int x, int y) {
		return x >= 1 && x <= 9 && y >= 1 && y <= 9;
	}

}
