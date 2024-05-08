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
        <button onClick={()=> pageNumber > 1 ? setPageNumber(pageNumber-1):null}>이전페이지</button>
        <button onClick={()=> pageNumber < numPages ? setPageNumber(pageNumber+1):null}>다음페이지</button> 
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
    background-color: #0A377C;
    z-index:2;
    border-radius: 15px;
`

export default PDFpreviewer;