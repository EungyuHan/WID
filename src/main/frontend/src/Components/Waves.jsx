import React, {useState} from 'react';
import styled,{keyframes} from 'styled-components';


// 스타일드 컴포넌트 사용하여 스타일 정의
const WavesContainer = styled.div`
    position: absolute;
    bottom: 0;
    width: 100%;
    height: 50%; 
    margin-bottom: -7px; /* Safari 갭 해결을 위한 수정 */
`;

const ParallaxWave = styled.svg`
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 100%;
`;

// Waves 컴포넌트 정의
const Waves = () => {
    return (
        <WavesContainer>
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

export default Waves;
