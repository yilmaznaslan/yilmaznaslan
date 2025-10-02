import { useEffect, useState } from "react";
import "./tictac.css";

interface Move {
  moveId: number;
  gridInfo: GridInfo;
}

interface GridInfo {
  playerName: string;
  coordinate: Coordinate;
}
interface Coordinate {
  colIndex: number;
  rowIndex: number;
}

function Square() {}
export default function TictactoeGame() {
  const initialGridInfo: GridInfo[] = [
    { playerName: "", coordinate: { colIndex: 0, rowIndex: 0 } },
    { playerName: "", coordinate: { colIndex: 1, rowIndex: 0 } },
    { playerName: "", coordinate: { colIndex: 2, rowIndex: 0 } },

    { playerName: "", coordinate: { colIndex: 0, rowIndex: 1 } },
    { playerName: "", coordinate: { colIndex: 1, rowIndex: 1 } },
    { playerName: "", coordinate: { colIndex: 2, rowIndex: 1 } },

    { playerName: "", coordinate: { colIndex: 0, rowIndex: 2 } },
    { playerName: "", coordinate: { colIndex: 1, rowIndex: 2 } },
    { playerName: "", coordinate: { colIndex: 2, rowIndex: 2 } },
  ];
  const [player, setPlayer] = useState("X");
  const [currentMoveId, setCurrentMoveId] = useState(0);
  const [movementList, setMovementList] = useState<Move[]>([]);
  const [gridINfo, setGridInfo] = useState<GridInfo[]>(initialGridInfo);

  // Update currentGridInfo after currentMoveId
  useEffect(() => {
    const activeMoves = movementList.slice(0, currentMoveId);
    const activeGridInfos = activeMoves.map((asd) => asd.gridInfo);

    const updated = initialGridInfo.map((cur) => {
      const matchingCoordinate = activeGridInfos.find(
        (ss) =>
          ss.coordinate.colIndex === cur.coordinate.colIndex &&
          ss.coordinate.rowIndex === cur.coordinate.rowIndex
      );
      console.log(matchingCoordinate);
      if (matchingCoordinate) {
        return matchingCoordinate;
      } else {
        return cur;
      }
    });

    setGridInfo(updated);
  }, [currentMoveId]);

  function handleOnClick(rowIndex: number, colIndex: number) {
    const isGirdOccupied = movementList.find(
      (move) =>
        move.gridInfo.coordinate.colIndex === colIndex &&
        move.gridInfo.coordinate.rowIndex === rowIndex
    );

    if (isGirdOccupied) {
      return;
    }
    const moveId = currentMoveId + 1;
    const playerName = player;
    const coordinate = { colIndex: colIndex, rowIndex: rowIndex };
    const gridInfo = { playerName, coordinate };
    const move = { moveId, gridInfo: gridInfo };

    const newMove = [...movementList, move];
    setMovementList(newMove);

    if (playerName === "X") {
      setPlayer("O");
    } else {
      setPlayer("X");
    }
    setCurrentMoveId(currentMoveId + 1);
  }

  return (
    <div className="flex-row">
      <div>
        {/* Game Header*/}
        <div>
          <p className="simple-text">Next Player {player}</p>
        </div>
        {/* Game Board */}
        <div className="ticTacToeGrid">
          {gridINfo.map((grid) => {
            return (
              <div
                key={`${grid.coordinate.colIndex}-${grid.coordinate.rowIndex}`}
                style={{ color: "black", border: "1px solid black" }}
                onClick={() =>
                  handleOnClick(
                    grid.coordinate.rowIndex,
                    grid.coordinate.colIndex
                  )
                }
              >
                {grid.playerName}
              </div>
            );
          })}
        </div>
      </div>

      {/* Historical List*/}
      <ol>
        <li className="simple-text" onClick={() => setCurrentMoveId(0)}>
          Go to game start
        </li>
        {movementList.slice(0, currentMoveId).map((move) => {
          return (
            <li
              className="simple-text"
              key={move.moveId}
              onClick={() => setCurrentMoveId(move.moveId)}
            >
              Go to move #{move.moveId}
            </li>
          );
        })}
      </ol>
    </div>
  );
}
