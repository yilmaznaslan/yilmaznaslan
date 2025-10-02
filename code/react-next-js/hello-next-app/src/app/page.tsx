"use client";
import { useState } from "react";
import CarSearchButton1 from "./carSearchButton1";
import CarSearchButton2 from "./carSearchButton2";
import TictactoeGame from "./tictactoeGame";

interface ButtonMetricsProps {
  car1Count: number;
  car2Count: number;
}
function ButtonMetrics({ car1Count, car2Count }: ButtonMetricsProps) {
  return (
    <div>
      <p>
        Button Metric show here {car1Count} and {car2Count}
      </p>
    </div>
  );
}

export default function Home() {
  const [car1Count, setCar1Count] = useState(0);
  const [car2Count, setCar2Count] = useState(0);

  return (
    <div className="bg-white">
      <TictactoeGame />;
    </div>
  );
}
