import React, { useState } from 'react';
import { Document, Page } from 'react-pdf';
import styled,{keyframes} from 'styled-components';

function PDFpreviewer(props) {
    const [numPages, setNumPages] = useState(null);
    const [pageNumber, setPageNumber] = useState(1);

    function onDocumentLoadSuccess({ numPages }) {
    setNumPages(numPages);
    }

    return (
    
    <DocumentDiv>
        <PDFcontroller> 
        <ControllerButton onClick={()=> pageNumber > 1 ? setPageNumber(pageNumber-1):null}>이전페이지</ControllerButton>
        <ControllerButton onClick={()=> pageNumber < numPages ? setPageNumber(pageNumber+1):null}>다음페이지</ControllerButton> 
        </PDFcontroller>
        <Document
        file="/img/(2021)프라이빗 블록체인을 사용한 DID 활용 연구.pdf"
        onLoadSuccess={onDocumentLoadSuccess}>
        <Page pageNumber={pageNumber} />
        </Document>

        
    
    </DocumentDiv>
    
    );
}



const DocumentDiv = styled.div`
    border-radius: 15px;
    position: relative;
    width: 90%;
    height: 93%;
    margin: auto;
    overflow: scroll;
`

const PDFcontroller = styled.div`
    position: fixed;
    height: 100px
    width: 200px; /* 예시로 지정한 너비 */
    z-index:2;
    border-radius: 15px;
`
const ControllerButton = styled.button`
    border-radius: 10px;
    padding: 10px 10px;
    border: none;
    box-shadow: 0.5px 0.5px 2px black;
    margin: 2px;
`

export default PDFpreviewer;