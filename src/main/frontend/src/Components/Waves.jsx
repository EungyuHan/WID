import React from 'react';
import styled, { keyframes } from 'styled-components';

const Waves = () => {
    return (
        <WavesContainer className="waves">
            <ParallaxWave xmlns="http://www.w3.org/2000/svg" viewBox="0 24 150 28" preserveAspectRatio="none">
                <defs>
                    <path id="gentle-wave" d="M-160 44c30 0 58-18 88-18s 58 18 88 18 58-18 88-18 58 18 88 18 v44h-352z"/>
                </defs>
                <g className="parallax">
                    <use xlinkHref="#gentle-wave" x="30" y="0" fill="rgba(255,255,255,0.7)"/>
                    <use xlinkHref="#gentle-wave" x="40" y="3" fill="rgba(255,255,255,0.5)"/>
                    <use xlinkHref="#gentle-wave" x="50" y="0" fill="rgba(255,255,255,0.3)"/>
                    <use xlinkHref="#gentle-wave" x="50" y="7" fill="#fff"/>
                </g>
            </ParallaxWave>
        </WavesContainer>
    );
};

const moveForever = keyframes`
    0% {
        transform: translate3d(-90px, 0, 0);
    }
    100% {
        transform: translate3d(85px, 0, 0);
    }
`;

const WavesContainer = styled.div`
    position: absolute;
    bottom: 0;
    width: 100%;
    height: 50vh;
    margin-bottom: -7px; /*Fix for safari gap*/
    min-height: 0%;
    max-height: %;
    overflow: hidden;
    z-index: -2;
`;

const ParallaxWave = styled.svg`
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 100%;

    .parallax > use {
        animation: ${moveForever} 25s cubic-bezier(0.55,.5,0.45,.5) infinite;
    }

    .parallax > use:nth-child(1) {
        animation-delay: -2s;
        animation-duration: 49s;
    }

    .parallax > use:nth-child(2) {
        animation-delay: -3s;
        animation-duration: 56s;
    }

    .parallax > use:nth-child(3) {
        animation-delay: -4s;
        animation-duration: 55s;
    }

    .parallax > use:nth-child(4) {
        animation-delay: -5s;
        animation-duration: 60s;
    }
`;

export default Waves;
