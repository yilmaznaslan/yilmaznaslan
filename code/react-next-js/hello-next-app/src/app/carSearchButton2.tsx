'use client';
import { useEffect, useState } from 'react';

interface MyButtonProp {
  onClick(carCount: number): void;
}

export default function CarSearchButton2({ onClick }: MyButtonProp) {
  const [carCount, setCarCount] = useState(0);

  useEffect(() => {
    console.log('Component 2 is mounted');

    return () => {
      console.log('Component 2 is unmounted');
    };
  });

  const handleButtonClick = () => {
    setCarCount(carCount + 1);
    onClick(carCount);
  };

  return (
    <div className="bg-blue-200 m-5 p-5">
      <button className="bg-blue-500 p-5 " onClick={handleButtonClick}>
        Button 2 Click here{' '}
      </button>
    </div>
  );
}
