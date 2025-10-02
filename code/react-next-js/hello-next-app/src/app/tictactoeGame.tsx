import { useState } from "react";
import "./tictac.css";

interface Move {
  moveId: number;
  playerName: string;
  colIndex: number;
  rowIndex: number;
}

export default function TictactoeGame() {
  const [nextPlayer, setNextPlayer] = useState("X");
  const [currentMoveId, setCurrentMoveId] = useState(0);
  const [movementList, setMovementList] = useState<Move[]>([]);

  const rowIndexes = [0, 1, 2];
  const columnIndexes = [0, 1, 2];

  function handleOnClick(rowIndex: number, colIndex: number) {
    const isGirdOccupied = movementList.find(
      (move) => move.colIndex === colIndex && move.rowIndex === rowIndex
    );

    console.log(rowIndex, colIndex, isGirdOccupied);
    if (isGirdOccupied) {
      return;
    }
    const moveId = currentMoveId + 1;
    const playerName = nextPlayer;
    const move = { moveId, playerName, colIndex, rowIndex };

    const newMove = [...movementList, move];
    setMovementList(newMove);

    if (playerName === "X") {
      setNextPlayer("O");
    } else {
      setNextPlayer("X");
    }
    setCurrentMoveId(currentMoveId + 1);
  }

  function getGridLetter(rowIndex: number, colIndex: number): string {
    return (
      movementList.find(
        (element) =>
          element.colIndex == colIndex && element.rowIndex === rowIndex
      )?.playerName ?? ""
    );
  }

  return (
    <div className="flex-row">
      <div>
        {/* Game Header*/}
        <div>
          <p>Next Player {nextPlayer}</p>
        </div>
        {/* Game Board */}
        <div className="ticTacToeGrid">
          {rowIndexes.map((i) => {
            return columnIndexes.map((j) => {
              return (
                <div
                  key={i + j}
                  className="border-1"
                  onClick={() => handleOnClick(i, j)}
                >
                  {getGridLetter(i, j)}
                </div>
              );
            });
          })}
        </div>
      </div>

      {/* Historical List*/}
      <ol>
        <li onClick={() => setCurrentMoveId(0)}>Go to game start</li>
        {movementList.map((move) => {
          return (
            <li key={move.moveId} onClick={() => setCurrentMoveId(move.moveId)}>
              Go to move #{move.moveId}
            </li>
          );
        })}
      </ol>
    </div>
  );
}
