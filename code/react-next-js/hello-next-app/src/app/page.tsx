'use client';
import { useState } from 'react';
import CarSearchButton1 from './carSearchButton1';
import CarSearchButton2 from './carSearchButton2';

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
    <div className="font-sans grid grid-rows-[20px_1fr_20px] items-center justify-items-center min-h-screen p-8 pb-20 gap-16 sm:p-20">
      <CarSearchButton1 onClick={setCar1Count} />
      <CarSearchButton2 onClick={setCar2Count} />
      <ButtonMetrics car1Count={car1Count} car2Count={car2Count} />
    </div>
  );
}
