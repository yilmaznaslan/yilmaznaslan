'use client';
import { useEffect, useState } from 'react';

interface MyButtonProp {
  onClick(carCount: number): void;
}

export default function CarSearchButton1({ onClick }: MyButtonProp) {
  const [carCount, setCarCount] = useState(0);

  useEffect(() => {
    console.log('Component is mounted');

    return () => {
      console.log('Component is unmounted');
    };
  });

  const handleButtonClick = () => {
    setCarCount(carCount + 1);
    onClick(carCount);
  };

  return (
    <div className="bg-red-200 m-5 p-5">
      <button className="bg-blue-500 p-5 " onClick={handleButtonClick}>
        Button 1 Click here{' '}
      </button>
    </div>
  );
}
