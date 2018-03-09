/*
 * Copyright 2016 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mz.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.xmpbox.XMPMetadata;
import org.apache.xmpbox.schema.DublinCoreSchema;
import org.apache.xmpbox.schema.PDFAIdentificationSchema;
import org.apache.xmpbox.schema.XMPBasicSchema;
import org.apache.xmpbox.type.BadFieldValueException;
import org.apache.xmpbox.xml.XmpSerializer;
import sun.misc.BASE64Decoder;

import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This example demonstrates the use of the new methods
 * {@link PDFMergerUtility#setDestinationDocumentInformation(org.apache.pdfbox.pdmodel.PDDocumentInformation) }
 * and
 * {@link PDFMergerUtility#setDestinationMetadata(org.apache.pdfbox.pdmodel.common.PDMetadata) }
 * that were added in April 2016. These allow to control the meta data in a
 * merge without having to reopen the result file.
 *
 * @author Alexander Kriegisch
 */
public class MergePdf {
    private static final Log LOG = LogFactory.getLog(MergePdf.class);
    private static FileUtil fileUtil = new FileUtil();

    public static void main(String[] args) throws IOException {
        //String str1 = fileUtil.getBase64FileToString("E:/base64.txt");
        //String str2 = fileUtil.getBase64FileToString("E:/base641.txt");
        List<String> base64Contents = new ArrayList<>();
        //base64Contents.add(str2);
        String str = "JVBERi0xLjQKJeLjz9MKMyAwIG9iago8PC9MZW5ndGggMTAvRmlsdGVyL0ZsYXRlRGVjb2RlPj5z\r\n" +
                "dHJlYW0KeJwr5AIAAO4AfAplbmRzdHJlYW0KZW5kb2JqCjQgMCBvYmoKPDwvTGVuZ3RoIDM3NC9G\r\n" +
                "aWx0ZXIvRmxhdGVEZWNvZGU+PnN0cmVhbQpIiaSSzWrDMBCE736KfQEruytpJUEIxJYDPeRSfC+l\r\n" +
                "dUtKfsCYPn8V57dpkiYEgS/SzM6Ov6LOBuVq2TXLDobDwbR8ioAwGhWxhAxhfdrPbPDczF+72XdT\r\n" +
                "ruardrZounb2Bu0safkFgaD+yHz/2AOLEnLAnhSyhnqRDamQkopqgumCZGxH9VdWTdOAP6NpNzpH\r\n" +
                "Rc5BTkrbZPKeTBADjo1GXcplA94ZECuBZOJ7MRdSGKZ/xHo/vVf3w3s5RhGMTq7LzU7ORpkUnpXl\r\n" +
                "TXa0FNYlGI/OVFFf9rD7CMlEQ+6UbCIQTca3dSiPduge6dA/1mE46RCV23ZIFD15iZoKQ+TjFYjw\r\n" +
                "kMEqdBZyUWHjYgQFgy9wzPF6EjqwGHY9ivg7eiQ+WmadImyKmOjImqp/xPqYhKSW9ef2/0DHNLLl\r\n" +
                "1GQw/k4a6RRHUdbdiSOd4xHv6vEhIOkXkRQ8pE22TJnE1I00hKMtDDLkVrltjrNkV3X2I8AAuNE/\r\n" +
                "6QplbmRzdHJlYW0KZW5kb2JqCjUgMCBvYmoKPDwvTGVuZ3RoIDIzOS9GaWx0ZXIvRmxhdGVEZWNv\r\n" +
                "ZGU+PnN0cmVhbQp4nH2SsY4DIQxE+/0Kf4HPNsZAnx9Ilz7VFadT/r+JNyywhbNConqaYWaA+/ba\r\n" +
                "XsCYgfwwagMlkEbw/IOfxy/B7R8cAkJTyyeMU4aJccfu4FodAVbDWkGsodiByVCLQKpIA0wR6BxV\r\n" +
                "4CKL05Arux5TW1wOjRt/jAsmPTiLOOGG5ndBHVyJuPzJ4aLTtl7I2d7owbXweUlQPYd6Dhk900UQ\r\n" +
                "LrSqZv5eoZChTclwFNG8S6aywvDFKGLe+VSMV+mgVJQRm8NZjvnkbB3u0hW9x+Uc7tILZ/JE0zlc\r\n" +
                "pgsmwzKweJj+YU1OH3vtsr0BKsy32QplbmRzdHJlYW0KZW5kb2JqCjEwIDAgb2JqCjw8L09yZGVy\r\n" +
                "aW5nKEdCMSkvUmVnaXN0cnkoQWRvYmUpL1N1cHBsZW1lbnQgND4+CmVuZG9iagoxMSAwIG9iago8\r\n" +
                "PC9UeXBlL0ZvbnREZXNjcmlwdG9yL0Rlc2NlbnQgLTE1Ni9TdGVtViA3Ni9Gb250V2VpZ2h0IDQw\r\n" +
                "MC9DYXBIZWlnaHQgNjg4L0ZvbnRCQm94Wy0xMiAtMTU2IDk5NiA4NTldL0ZvbnRTdHJldGNoL05v\r\n" +
                "cm1hbC9GbGFncyAzMi9YSGVpZ2h0IDQ1Ny9Gb250RmFtaWx5KP7/ntFPUykvQXNjZW50IDg1OS9G\r\n" +
                "b250TmFtZS9TaW1IZWkvSXRhbGljQW5nbGUgMD4+CmVuZG9iago5IDAgb2JqCjw8L0Jhc2VGb250\r\n" +
                "L1NpbUhlaS9DSURTeXN0ZW1JbmZvIDEwIDAgUi9XWzBbMTAwMF0gMSA5NSA1MDAgNjY4IDY5OSA1\r\n" +
                "MDBdL1R5cGUvRm9udC9TdWJ0eXBlL0NJREZvbnRUeXBlMi9Gb250RGVzY3JpcHRvciAxMSAwIFIv\r\n" +
                "RFcgMTAwMD4+CmVuZG9iago4IDAgb2JqCls5IDAgUl0KZW5kb2JqCjcgMCBvYmoKPDwvRGVzY2Vu\r\n" +
                "ZGFudEZvbnRzIDggMCBSL0Jhc2VGb250L1NpbUhlaS9UeXBlL0ZvbnQvU3VidHlwZS9UeXBlMC9F\r\n" +
                "bmNvZGluZy9VbmlHQi1VVEYxNi1IPj4KZW5kb2JqCjYgMCBvYmoKPDwvVHlwZS9YT2JqZWN0L1Jl\r\n" +
                "c291cmNlczw8L1Byb2NTZXRbL1BERi9UZXh0L0ltYWdlQi9JbWFnZUMvSW1hZ2VJXS9Gb250PDwv\r\n" +
                "U2ltSGVpIDcgMCBSPj4+Pi9TdWJ0eXBlL0Zvcm0vQkJveFswIDAgOTYuNSAxMC44Ml0vTWF0cml4\r\n" +
                "WzEgMCAwIDEgMCAwXS9Gb3JtVHlwZSAxL0xlbmd0aCA5NS9GaWx0ZXIvRmxhdGVEZWNvZGU+PnN0\r\n" +
                "cmVhbQp4nB2MMQqAMBAEvzKlNtEElaSNCDYW4oEvUFFQiJWlT/eQrWYHJjFSyEMcWhKlLjSmxpbG\r\n" +
                "O+6FmUv/KNhfWhzW+ICcFNN+9suOR1ZVG5lU75HL8UMnWh7ptPsBkW4XkwplbmRzdHJlYW0KZW5k\r\n" +
                "b2JqCjEyIDAgb2JqCjw8L1R5cGUvWE9iamVjdC9SZXNvdXJjZXM8PC9Qcm9jU2V0Wy9QREYvVGV4\r\n" +
                "dC9JbWFnZUIvSW1hZ2VDL0ltYWdlSV0vRm9udDw8L1NpbUhlaSA3IDAgUj4+Pj4vU3VidHlwZS9G\r\n" +
                "b3JtL0JCb3hbMCAwIDc3LjQgMThdL01hdHJpeFsxIDAgMCAxIDAgMF0vRm9ybVR5cGUgMS9MZW5n\r\n" +
                "dGggMTAzL0ZpbHRlci9GbGF0ZURlY29kZT4+c3RyZWFtCnicK1QIVNAPqVBw8nVWKFQwAEJzcz0T\r\n" +
                "BUMLhaJUhXCFPKCgU4iCIVjGUMHQTM/STMFYz9xIISRXQT84M9cjNVPBEMhLA8qnK2j8f8pgwKAH\r\n" +
                "xAaBzpohWWBB1xCgHYEKrkAbAInPGbwKZW5kc3RyZWFtCmVuZG9iagoxMyAwIG9iago8PC9UeXBl\r\n" +
                "L1hPYmplY3QvUmVzb3VyY2VzPDwvUHJvY1NldFsvUERGL1RleHQvSW1hZ2VCL0ltYWdlQy9JbWFn\r\n" +
                "ZUldL0ZvbnQ8PC9TaW1IZWkgNyAwIFI+Pj4+L1N1YnR5cGUvRm9ybS9CQm94WzAgMCAxNzcuMTIg\r\n" +
                "MzguODhdL01hdHJpeFsxIDAgMCAxIDAgMF0vRm9ybVR5cGUgMS9MZW5ndGggMTA3L0ZpbHRlci9G\r\n" +
                "bGF0ZURlY29kZT4+c3RyZWFtCnicK1QIVNAPqVBw8nVWKFQwAEJDc3M9QyMFYws9CwuFolSFcIU8\r\n" +
                "BacQBUOIpAJQxkjPwkQhJFdBPzgz1yM1U8FCISQNKJeuoJH7Lre89z2DIYMBEOsymAR/1wzJAku5\r\n" +
                "hgAtcgVaAgDwhBu0CmVuZHN0cmVhbQplbmRvYmoKMTQgMCBvYmoKPDwvVHlwZS9YT2JqZWN0L1Jl\r\n" +
                "c291cmNlczw8L1Byb2NTZXRbL1BERi9UZXh0L0ltYWdlQi9JbWFnZUMvSW1hZ2VJXS9Gb250PDwv\r\n" +
                "U2ltSGVpIDcgMCBSPj4+Pi9TdWJ0eXBlL0Zvcm0vQkJveFswIDAgNjcuNyAxNi41OF0vTWF0cml4\r\n" +
                "WzEgMCAwIDEgMCAwXS9Gb3JtVHlwZSAxL0xlbmd0aCA5Mi9GaWx0ZXIvRmxhdGVEZWNvZGU+PnN0\r\n" +
                "cmVhbQp4nB2MsQqAIBQAf+XGWkxD07kIWhqiB31BRYFBTY19eiI3HQd3M1HJSzt23OhE45XHNMoF\r\n" +
                "npWFi1YwORlqjFbOIpFqPuKwHgRkS22nEPudpZxZeknjPk1/Q64WwgplbmRzdHJlYW0KZW5kb2Jq\r\n" +
                "CjE1IDAgb2JqCjw8L1R5cGUvWE9iamVjdC9SZXNvdXJjZXM8PC9Qcm9jU2V0Wy9QREYvVGV4dC9J\r\n" +
                "bWFnZUIvSW1hZ2VDL0ltYWdlSV0vRm9udDw8L1NpbUhlaSA3IDAgUj4+Pj4vU3VidHlwZS9Gb3Jt\r\n" +
                "L0JCb3hbMCAwIDQ5LjcgMzMuMTddL01hdHJpeFsxIDAgMCAxIDAgMF0vRm9ybVR5cGUgMS9MZW5n\r\n" +
                "dGggOTEvRmlsdGVyL0ZsYXRlRGVjb2RlPj5zdHJlYW0KeJwdjMEKQEAUAH9ljlyWh1pOaqVcHOTF\r\n" +
                "FyBqFSefb2lO09RcDCT64PqGizRQVMaS50Ys98LMiVPkT0JGVppUUE8y7r5bdiy6hrYRTfVHrMev\r\n" +
                "rYZ1G7YvbMcXCQplbmRzdHJlYW0KZW5kb2JqCjE2IDAgb2JqCjw8L1R5cGUvWE9iamVjdC9SZXNv\r\n" +
                "dXJjZXM8PC9Qcm9jU2V0Wy9QREYvVGV4dC9JbWFnZUIvSW1hZ2VDL0ltYWdlSV0vRm9udDw8L1Np\r\n" +
                "bUhlaSA3IDAgUj4+Pj4vU3VidHlwZS9Gb3JtL0JCb3hbMCAwIDU4LjcgMTYuNThdL01hdHJpeFsx\r\n" +
                "IDAgMCAxIDAgMF0vRm9ybVR5cGUgMS9MZW5ndGggMTA1L0ZpbHRlci9GbGF0ZURlY29kZT4+c3Ry\r\n" +
                "ZWFtCnicHc2xCoMwGEXh+yhnbJdotInJahG6OJT+0CdQsaBgpz5+g5zxG87Bk8p+9OOdg7oUkuvw\r\n" +
                "0YXEd+LNTm/4kzwN2cWIbVSvdXtMKxmbCy1c5NWo1U1BUZ2SsuqrfU4crHyG8vgDYFgXOQplbmRz\r\n" +
                "dHJlYW0KZW5kb2JqCjE3IDAgb2JqCjw8L1R5cGUvWE9iamVjdC9SZXNvdXJjZXM8PC9Qcm9jU2V0\r\n" +
                "Wy9QREYvVGV4dC9JbWFnZUIvSW1hZ2VDL0ltYWdlSV0vRm9udDw8L1NpbUhlaSA3IDAgUj4+Pj4v\r\n" +
                "U3VidHlwZS9Gb3JtL0JCb3hbMCAwIDk2LjUgMTAuODJdL01hdHJpeFsxIDAgMCAxIDAgMF0vRm9y\r\n" +
                "bVR5cGUgMS9MZW5ndGggOTUvRmlsdGVyL0ZsYXRlRGVjb2RlPj5zdHJlYW0KeJwdjDEKgDAQBL8y\r\n" +
                "pTbRBJWkjQg2FuKBL1BRUIiVpU/3kK1mByYxUshDHFoSpS40psaWxjvuhZlL/yjYX1oc1viAnBTT\r\n" +
                "fvbLjkdWVRuZVO+Ry/FDJ1oe6bT7AZFuF5MKZW5kc3RyZWFtCmVuZG9iagoxOCAwIG9iago8PC9U\r\n" +
                "eXBlL1hPYmplY3QvUmVzb3VyY2VzPDwvUHJvY1NldFsvUERGL1RleHQvSW1hZ2VCL0ltYWdlQy9J\r\n" +
                "bWFnZUldL0ZvbnQ8PC9TaW1IZWkgNyAwIFI+Pj4+L1N1YnR5cGUvRm9ybS9CQm94WzAgMCA3Ny40\r\n" +
                "IDE4XS9NYXRyaXhbMSAwIDAgMSAwIDBdL0Zvcm1UeXBlIDEvTGVuZ3RoIDEwMy9GaWx0ZXIvRmxh\r\n" +
                "dGVEZWNvZGU+PnN0cmVhbQp4nCtUCFTQD6lQcPJ1VihUMABCc3M9EwVDC4WiVIVwhTygoFOIgiFY\r\n" +
                "xlDB0EzP0kzBWM/cSCEkV0E/ODPXIzVTwRDISwPKpyto/H/KYMCgB8QGgc6aIVlgQdcQoB2BCq5A\r\n" +
                "GwCJzxm8CmVuZHN0cmVhbQplbmRvYmoKMTkgMCBvYmoKPDwvVHlwZS9YT2JqZWN0L1Jlc291cmNl\r\n" +
                "czw8L1Byb2NTZXRbL1BERi9UZXh0L0ltYWdlQi9JbWFnZUMvSW1hZ2VJXS9Gb250PDwvU2ltSGVp\r\n" +
                "IDcgMCBSPj4+Pi9TdWJ0eXBlL0Zvcm0vQkJveFswIDAgNzYuNyA2LjE3XS9NYXRyaXhbMSAwIDAg\r\n" +
                "MSAwIDBdL0Zvcm1UeXBlIDEvTGVuZ3RoIDEwNS9GaWx0ZXIvRmxhdGVEZWNvZGU+PnN0cmVhbQp4\r\n" +
                "nB2NsQqDQBBE91NeqUXOuwNvSasIaSwkC/kCFQMKWuXzs8jADMwr3slEYz+6seckerQEpYSkXDMf\r\n" +
                "Dr87I90skYlBM7bTvLf9NW8UbHG0UkmWKElUHr5P7yxtbd8bDuaiicE1f648F90KZW5kc3RyZWFt\r\n" +
                "CmVuZG9iagoyMCAwIG9iago8PC9UeXBlL1hPYmplY3QvUmVzb3VyY2VzPDwvUHJvY1NldFsvUERG\r\n" +
                "L1RleHQvSW1hZ2VCL0ltYWdlQy9JbWFnZUldL0ZvbnQ8PC9TaW1IZWkgNyAwIFI+Pj4+L1N1YnR5\r\n" +
                "cGUvRm9ybS9CQm94WzAgMCA5Ni41IDEwLjgyXS9NYXRyaXhbMSAwIDAgMSAwIDBdL0Zvcm1UeXBl\r\n" +
                "IDEvTGVuZ3RoIDk1L0ZpbHRlci9GbGF0ZURlY29kZT4+c3RyZWFtCnicHYwxCoAwEAS/MqU20QSV\r\n" +
                "pI0INhbigS9QUVCIlaVP95CtZgcmMVLIQxxaEqUuNKbGlsY77oWZS/8o2F9aHNb4gJwU0372y45H\r\n" +
                "VlUbmVTvkcvxQydaHum0+wGRbheTCmVuZHN0cmVhbQplbmRvYmoKMjEgMCBvYmoKPDwvVHlwZS9Y\r\n" +
                "T2JqZWN0L1Jlc291cmNlczw8L1Byb2NTZXRbL1BERi9UZXh0L0ltYWdlQi9JbWFnZUMvSW1hZ2VJ\r\n" +
                "XS9Gb250PDwvU2ltSGVpIDcgMCBSPj4+Pi9TdWJ0eXBlL0Zvcm0vQkJveFswIDAgMTc1LjcgMzMu\r\n" +
                "MTddL01hdHJpeFsxIDAgMCAxIDAgMF0vRm9ybVR5cGUgMS9MZW5ndGggMTE2L0ZpbHRlci9GbGF0\r\n" +
                "ZURlY29kZT4+c3RyZWFtCnicK1QIVNAPqVBw8nVWKFQwAEJDc1M9cwVjYz1Dc4WiVIVwhTwFpxAF\r\n" +
                "Q4icgpGCkbmeobFCSK6CfnBmrkdqpoKFQkgaUC5dQcMvJi+3PI5pan10Y7BV3dPoxt73DJZAaBH8\r\n" +
                "XTMkC6zGNQRooSvQMgAU8iAFCmVuZHN0cmVhbQplbmRvYmoKMjIgMCBvYmoKPDwvVHlwZS9YT2Jq\r\n" +
                "ZWN0L1Jlc291cmNlczw8L1Byb2NTZXRbL1BERi9UZXh0L0ltYWdlQi9JbWFnZUMvSW1hZ2VJXS9G\r\n" +
                "b250PDwvU2ltSGVpIDcgMCBSPj4+Pi9TdWJ0eXBlL0Zvcm0vQkJveFswIDAgNjkuMTIgMTYuNThd\r\n" +
                "L01hdHJpeFsxIDAgMCAxIDAgMF0vRm9ybVR5cGUgMS9MZW5ndGggMTA1L0ZpbHRlci9GbGF0ZURl\r\n" +
                "Y29kZT4+c3RyZWFtCnicHY2xCsJAEAX3U6bU5pI9zZJrIwGbFMEFvyBKhAhJ5ee7HA/mFVPMzkzj\r\n" +
                "P4bpxk4bs5I0o5a6nmPhyZfB0eqUTElm+EbzWLf7slLwV6g3J1G5SB+0+ldpJUt39k/Vo0dqjMwf\r\n" +
                "n6gXkwplbmRzdHJlYW0KZW5kb2JqCjIzIDAgb2JqCjw8L1R5cGUvWE9iamVjdC9SZXNvdXJjZXM8\r\n" +
                "PC9Qcm9jU2V0Wy9QREYvVGV4dC9JbWFnZUIvSW1hZ2VDL0ltYWdlSV0vRm9udDw8L1NpbUhlaSA3\r\n" +
                "IDAgUj4+Pj4vU3VidHlwZS9Gb3JtL0JCb3hbMCAwIDc2LjcgNi4xN10vTWF0cml4WzEgMCAwIDEg\r\n" +
                "MCAwXS9Gb3JtVHlwZSAxL0xlbmd0aCAxMDUvRmlsdGVyL0ZsYXRlRGVjb2RlPj5zdHJlYW0KeJwd\r\n" +
                "jbEKg0AQRPdTXqlFzrsDb0mrCGksJAv5AhUDClrl87PIwAzMK97JRGM/urHnJHq0BKWEpFwzHw6/\r\n" +
                "OyPdLJGJQTO207y3/TVvFGxxtFJJlihJVB6+T+8sbW3fGw7moonBNX+uPBfdCmVuZHN0cmVhbQpl\r\n" +
                "bmRvYmoKMjQgMCBvYmoKPDwvVHlwZS9YT2JqZWN0L1Jlc291cmNlczw8L1Byb2NTZXRbL1BERi9U\r\n" +
                "ZXh0L0ltYWdlQi9JbWFnZUMvSW1hZ2VJXS9Gb250PDwvU2ltSGVpIDcgMCBSPj4+Pi9TdWJ0eXBl\r\n" +
                "L0Zvcm0vQkJveFswIDAgNjYuNiAyNS4yXS9NYXRyaXhbMSAwIDAgMSAwIDBdL0Zvcm1UeXBlIDEv\r\n" +
                "TGVuZ3RoIDk4L0ZpbHRlci9GbGF0ZURlY29kZT4+c3RyZWFtCnicHYwxCoQwFAVzlCndJvGHNaRW\r\n" +
                "AjYWsh/2BCpZcEErj+9HXjVvYA5mgl7008BBa0vJJ2LnI+fCl7/dvSKPEySbiD5ndCd86j4ulfhG\r\n" +
                "V9MbjRMXnLz092BRy88Ui9/j8RdDCmVuZHN0cmVhbQplbmRvYmoKMjYgMCBvYmoKPDwvQmFzZUZv\r\n" +
                "bnQvSGVsdmV0aWNhL1R5cGUvRm9udC9FbmNvZGluZy9XaW5BbnNpRW5jb2RpbmcvU3VidHlwZS9U\r\n" +
                "eXBlMT4+CmVuZG9iagoyNSAwIG9iago8PC9UeXBlL1hPYmplY3QvUmVzb3VyY2VzPDwvUHJvY1Nl\r\n" +
                "dFsvUERGL1RleHQvSW1hZ2VCL0ltYWdlQy9JbWFnZUldL0ZvbnQ8PC9GMSAyNiAwIFI+Pj4+L1N1\r\n" +
                "YnR5cGUvRm9ybS9CQm94WzAgMCAxNjAgMzMuNjZdL01hdHJpeFsxIDAgMCAxIDAgMF0vRm9ybVR5\r\n" +
                "cGUgMS9MZW5ndGggMjg1L0ZpbHRlci9GbGF0ZURlY29kZT4+c3RyZWFtCnicjVMxbgQhEOt5BWVS\r\n" +
                "hGAYBmgjJS9A+UGuiJQm/y+yp1uW1XqKaJuVxdjGY6LvQdUjqE/if79cCvKAYmg7JNvfBWoTuh9/\r\n" +
                "QEjXQ8gb64UelbEUr1zJkEzKzhK5yAi7j7z97FgJ5C3rVFg+JLKqcBp1si3VEqe3xVYSK5RizLY5\r\n" +
                "uzDFTGnpqjCfVr5ZNbxU4ewqKdTObO3IZOXZhLffyFsHs/WjEWuyK6feDyen5kTwMGLhpBC5nGA+\r\n" +
                "GKEARi+AzmtDynw/pGJ4TN14B9l4LsKbwyrqidFqKsQoCMRoCMQyVIzdoBjLwWrsaVz3Rxz/Ddzc\r\n" +
                "23CvH/DNj5vDhkcPLwi93YnVjx/39DmySi05V4nygu17Ht/ufbg/NvfwyQplbmRzdHJlYW0KZW5k\r\n" +
                "b2JqCjI3IDAgb2JqCjw8L1R5cGUvWE9iamVjdC9SZXNvdXJjZXM8PC9Qcm9jU2V0Wy9QREYvVGV4\r\n" +
                "dC9JbWFnZUIvSW1hZ2VDL0ltYWdlSV0vRm9udDw8L0YxIDI2IDAgUj4+Pj4vU3VidHlwZS9Gb3Jt\r\n" +
                "L0JCb3hbMCAwIDExNiAzMy42Nl0vTWF0cml4WzEgMCAwIDEgMCAwXS9Gb3JtVHlwZSAxL0xlbmd0\r\n" +
                "aCAyMzMvRmlsdGVyL0ZsYXRlRGVjb2RlPj5zdHJlYW0KeJyNkjFuRCEMRHtO4TLbENsYA+1K2ROg\r\n" +
                "3CBbREqT+xdhtZ9F+kORDj3hmWEMU4vuJNFJjX6/gkZ7Io71QDZOJ1Qnelx/ItHzJUlD9SQvBZny\r\n" +
                "WUs3luqYTCFFknjkSONwsBwhW/LpsHIYo6thG2WqLdfMM9tSy4oOOW9m65xdzGW2tHzdUM8Lvqxs\r\n" +
                "shTD7go4lIZq9dXJ6rMabr9Ctiao1l4/Yk22zSZaw56EN19TOG/+GDe0EVHcrohhWSKzmX+De7j2\r\n" +
                "8H4TqtTvQQZnEtKxWX0IO/Wf8PbZk1vJKRVju/Tv8NHDH3HasnIKZW5kc3RyZWFtCmVuZG9iagoy\r\n" +
                "OCAwIG9iago8PC9UeXBlL1hPYmplY3QvUmVzb3VyY2VzPDwvUHJvY1NldFsvUERGL1RleHQvSW1h\r\n" +
                "Z2VCL0ltYWdlQy9JbWFnZUldL0ZvbnQ8PC9TaW1IZWkgNyAwIFI+Pj4+L1N1YnR5cGUvRm9ybS9C\r\n" +
                "Qm94WzAgMCA2Ny43IDkuMzhdL01hdHJpeFsxIDAgMCAxIDAgMF0vRm9ybVR5cGUgMS9MZW5ndGgg\r\n" +
                "MTA2L0ZpbHRlci9GbGF0ZURlY29kZT4+c3RyZWFtCnicHY0xCoNAFET/UV6pzbqrie62ipAmheRD\r\n" +
                "TqCioKBVjp/PMjAD84p3MVHpj/49cOEtbec6kmsi98yX0+5eCZkFaoLzET2oPtvxmjcSuhhaKSRI\r\n" +
                "I9G6zfsQL7U8S90zHtVUE6OJ/t4bGCsKZW5kc3RyZWFtCmVuZG9iagoyOSAwIG9iago8PC9UeXBl\r\n" +
                "L1hPYmplY3QvUmVzb3VyY2VzPDwvUHJvY1NldFsvUERGL1RleHQvSW1hZ2VCL0ltYWdlQy9JbWFn\r\n" +
                "ZUldL0ZvbnQ8PC9TaW1IZWkgNyAwIFI+Pj4+L1N1YnR5cGUvRm9ybS9CQm94WzAgMCAxNzUuNyAy\r\n" +
                "Mi4zN10vTWF0cml4WzEgMCAwIDEgMCAwXS9Gb3JtVHlwZSAxL0xlbmd0aCAxMTUvRmlsdGVyL0Zs\r\n" +
                "YXRlRGVjb2RlPj5zdHJlYW0KeJwrVAhU0A+pUHDydVYoVDAAQkNzUz1zBSMjPWNzhaJUhXCFPAWn\r\n" +
                "EAVDiJyCEVBez8hQISRXQT84M9cjNVPBXCEkDSiXrqDhF5OXWx7HNLU+ujHYqu5pdGPvewZLILQI\r\n" +
                "/q4ZkgVW4xoCtNAVaBkAFC4gAgplbmRzdHJlYW0KZW5kb2JqCjMwIDAgb2JqCjw8L1R5cGUvWE9i\r\n" +
                "amVjdC9SZXNvdXJjZXM8PC9Qcm9jU2V0Wy9QREYvVGV4dC9JbWFnZUIvSW1hZ2VDL0ltYWdlSV0+\r\n" +
                "Pi9TdWJ0eXBlL0Zvcm0vQkJveFswIDAgNjcuNyA5LjM4XS9NYXRyaXhbMSAwIDAgMSAwIDBdL0Zv\r\n" +
                "cm1UeXBlIDEvTGVuZ3RoIDIyL0ZpbHRlci9GbGF0ZURlY29kZT4+c3RyZWFtCnicK1QIVNAPqVBw\r\n" +
                "8nVWcAViACOsBAUKZW5kc3RyZWFtCmVuZG9iagozMSAwIG9iago8PC9UeXBlL1hPYmplY3QvUmVz\r\n" +
                "b3VyY2VzPDwvUHJvY1NldFsvUERGL1RleHQvSW1hZ2VCL0ltYWdlQy9JbWFnZUldPj4vU3VidHlw\r\n" +
                "ZS9Gb3JtL0JCb3hbMCAwIDY5LjEyIDkuMzhdL01hdHJpeFsxIDAgMCAxIDAgMF0vRm9ybVR5cGUg\r\n" +
                "MS9MZW5ndGggMjIvRmlsdGVyL0ZsYXRlRGVjb2RlPj5zdHJlYW0KeJwrVAhU0A+pUHDydVZwBWIA\r\n" +
                "I6wEBQplbmRzdHJlYW0KZW5kb2JqCjMyIDAgb2JqCjw8L1R5cGUvWE9iamVjdC9SZXNvdXJjZXM8\r\n" +
                "PC9Qcm9jU2V0Wy9QREYvVGV4dC9JbWFnZUIvSW1hZ2VDL0ltYWdlSV0vRm9udDw8L1NpbUhlaSA3\r\n" +
                "IDAgUj4+Pj4vU3VidHlwZS9Gb3JtL0JCb3hbMCAwIDExOC4xIDkuMzhdL01hdHJpeFsxIDAgMCAx\r\n" +
                "IDAgMF0vRm9ybVR5cGUgMS9MZW5ndGggMTEyL0ZpbHRlci9GbGF0ZURlY29kZT4+c3RyZWFtCnic\r\n" +
                "JY2xCsJAEET3U16pzeXWO+OlTQjYWAQX/YJEIiSQVH6+izLFDPMGZmOgsg/trWMjulRLUJqQCvvI\r\n" +
                "k9X71tA/5ISGWLCF6j4v13GmwSZHLw7yEJMktWS5yNlTcs8SJR/t/Zv05n8Dvb99AVW+GQEKZW5k\r\n" +
                "c3RyZWFtCmVuZG9iagozNiAwIG9iago8PC9PcmRlcmluZyhJZGVudGl0eSkvUmVnaXN0cnkoQWRv\r\n" +
                "YmUpL1N1cHBsZW1lbnQgMD4+CmVuZG9iagozOCAwIG9iago8PC9MZW5ndGggMTAxL0ZpbHRlci9G\r\n" +
                "bGF0ZURlY29kZT4+c3RyZWFtCkiJamAYGMBEvFIH/NKMFLmDZCCARYwFuzC1ARMpoYYKWHDKOJBt\r\n" +
                "UgMQK5DhFpoD3J6llUYiAQcan85Jd4AA2Yl22AGBgXYAtQB6Qh4FIxA0QCiBgXQDxYXL8C2CBQAC\r\n" +
                "DAB+XAKiCmVuZHN0cmVhbQplbmRvYmoKMzkgMCBvYmoKPDwvTGVuZ3RoMSAxODAyOTAvTGVuZ3Ro\r\n" +
                "IDIwOTE4L0ZpbHRlci9GbGF0ZURlY29kZT4+c3RyZWFtCkiJ7FZbjBxHFb3Vj6rurupH9XN2Znae\r\n" +
                "6xmv1+sd78zOWDh+rl84xBYY7E2cQBZ7bYO8fgQTgkmwSQARRZac5AM+iMJDhA8+iJBCgogSEEEQ\r\n" +
                "AQFixAdyRPgIQUiIBCuJxIeX2z0za8UGiX+omZrq6qqePveec+8tIADA4Tyo0Nq7b2r60mOjebzz\r\n" +
                "LPY7D919psLG1TcASBFAee7IqaOLu7/+yF8A1D/gun30+KeP1D687wTOXwcIS8cW5g+/8qLxBEBO\r\n" +
                "x/XuMbxBrmodnK/H+dixxTP33Hvobw/h/CMAr718/OSheTr11V0AV9cCee59i/P3nDq9R9kIyrP3\r\n" +
                "4f7KifnFhbna6t/j/DF8/5VTdy2c0i/v/SUoz1/E9e8h5vPkIuhgIP7zeMcbjOdBwBZ8glCTkrSd\r\n" +
                "x0fW/fref+JajB1uu3X7LGyGytJrcHnpMoxB2jVIfQHwSXgyRQtk6f/tf7rBd0CBU9i1patLf8Q5\r\n" +
                "RaVZqC0HleZDiFrKQR6KUErv/Md13PFfvKuEnxdRd0X8KEt/hQ9BG9bBj/H/NkMPNsA03IRafQUO\r\n" +
                "wC0Yr+WsP4IrZxHhcTgJFfzk8Y0phrS7T65cffMH5rZvK1Srt06iEcWlK/Aq/qEKDCCpz7RZPWq/\r\n" +
                "+sILxlNP2S+xH+GOFr7+/TDf39FrR7ge1VtVbPPVchWjHMylP8MV+A2+aDVgWHebcUKTbi+hLI67\r\n" +
                "PZzQRqNJWbPba7AoTOrdmRnKGs1uuoar6WXvHKFS0Vt5nW8VU4xwzuz6uN0wqVdveSQirkVNr2a4\r\n" +
                "xhlmmqZjGE9HRBGzphEQZ1MrtplS9EVnk6EGCts5cS6K4+hgThjM3dpgSp5TJqXO0AJr6fUMq42O\r\n" +
                "qsMqxLsRdiLmFNdMPUJsnWb7RpgJrvUa9RpDB2VD3J7uRctXjaEZfyLB/pCQ+x61SawKTk1eMbh9\r\n" +
                "dv/ZcP366HFXTk7GwZop6UytiaOplr2ZqqbJdMtoFwuOjNxC0b2/9mBJ+qWFnGdRsaGmac/UahcI\r\n" +
                "kTPYCelkv4cdQ1UiXVMBSZVo0RvwMqamlej/Flp0E/KU0pQhrGeW9IG2Z/r29UlhmVHY2WDsDoy4\r\n" +
                "dPPW2JPRVhIrW3LcSmZ3EXKIG5atCVro9XpBp9MZtTmzLGZ880iOhKRs22UccrJSiZKcTvXI2l8o\r\n" +
                "TOTzE9nvnpyuxUKjqBUX0b4JPwUTmjAx8HuGKemjpaxNsSGOGB1fx2+GPIX3c/IwIQ/TwHWDRU2/\r\n" +
                "W1Wp39LoWEDVT5l6LvL96NzHvh8E0glCeTvxiSO9uMxVLogSJLkc3mk0Gpj/QaBeL6PHJmEtIsje\r\n" +
                "iD26JoEh76kzYlRy/V06OMn0T4iJlQuERIshqdqamd8kY8lYxRoJD5YkFUnVsc19iz+xmN8bcb1x\r\n" +
                "BJI4/rSjWaaet0OURd4ISmM2ZW6zxNQfttuIii+9Ce/Ab5G929IY6vZSryCq4djt4U/cR9JEhAOJ\r\n" +
                "4thuT/cZrddr6QpLt9I+lzOZ+wZ/kbn4gMY95KqUBtUKtMFDP5pEujgvOZstqlqCOu7emCDZjk/k\r\n" +
                "hKsyNwiECEhMfM79o0yhiZ9Egc9XSonGarZImnm0jissmND1WV/4YgPfF9oeU2K3cEDTuprBTeoY\r\n" +
                "FtfoHAlIRUo7quBFyoe59Ba8DS9hqppFy1n0LrhDGV+zY0DGsqb7mwfbMjd9gdQQ62gUlsIgivy5\r\n" +
                "XBD4JN5ekapmBtQzGDUkbtC563Kdx8qF3Ilv+LiniYA+jh2/J2Pvi9ywhaIr+9BbFem6MsW7V2Om\r\n" +
                "GOSRd+B3mEfWYMStQ9Y2w7Yb8khfRH0m4oT1pRbHPbyDtynrpuLK4jB9aDj+ggSnEcSXf2COrPcT\r\n" +
                "V3gma8YWN1R20hwNvQ0JVcdaI8UNwQfzUTKnLrT8qdPYN9VrgW0HtbqvWoqwAldgmihNUs1QTVl1\r\n" +
                "DUtB75u27Wg7fLuofiaHrZFgS3N4as/bGBPvgdvhSD8qrnPsdeMwoQzH5RBpDKxMWGbiMJC6vXRn\r\n" +
                "r5vWBYypNKgou6iTIurOkxi2nuNhc/KB5wW3BFL6VV+6nudXqGpYJlUd872GOq4Lw2OeJljOplwU\r\n" +
                "LFbSbc13Y0fVSZw3KTOURDfUZ6QVxtwOQxIoHaRtS5/VdOjkTVUprvAVxWxYzRkpmEcFL3Jqscg3\r\n" +
                "YjEW4DOElz3tIabHzSAsc9RoiIfCf6B/VmLRxeyK2JcdMshWzaEvOqmx/etugh68ThG9nxmlYJsV\r\n" +
                "x9Y2kksITQTZ4bmuu1MxY2+PL4RsawcJCb+NdeQOZhnm533Nd0wSKu2QKpajqGIVmmEamsNxnGGu\r\n" +
                "wexCEUuiw02scXpWj1NtTsJW2AF78NAAvWu5rdNcJmWZs6EwswJAo96NoPupr8HaWSlvsCwt/kqJ\r\n" +
                "jkaEsDGVjbUikRMus6Iys21q3MnDyJlawZRAKFXMMt+NCVlQdddkZPyoIi+Uy7b0KBnxA2c3dyPX\r\n" +
                "i5yECkzWXMeyGQph6XZVUk0zZYIZVRG2rlr8cYkVfbRkmWZoIYuh9vTo6FqNCd2gs0rEHInHdZkx\r\n" +
                "dQmVvAvuwHML9IaWZXrss9ZPpPEwiTSuUdbuzmT+yLZd54d/x2bqlgcU4qyOdc1elXPq3lfcMEDd\r\n" +
                "RYpeIJ/zEt/V9JFxPUwLVJKGIKl50nc8EmxETR729Dkk+1sp2SrzmLrxAY2onBqm7lCsuFi1tgUY\r\n" +
                "wJxY2/HaZIpnEY+YXEEF4MWc50puW5i9viS4YYhcwbEswxKK9jwGtoG17u/wFurWWz4frMNMtSvN\r\n" +
                "s31jrh0IojBeriSs3mh0m1nZHZ4ShkecYTYbnhbuUrESPkHIlKpyp6zb00wnhq0bzm5PR+I/+tm0\r\n" +
                "fS2w2L+Yr5YYOY4y3D3dXVXdXVX97p5Hz85zZ2az67W96/V4H2Szthx78Ss22HmA7UQJAgmTGClx\r\n" +
                "ZIIIF5BIJEBIcEEQgQQSIA65cOAZuAACJAQcQPgIBEUghMASl2j5q+efdWfJwREcGM2n6vrr+b//\r\n" +
                "inzGvk5tlzPmXqEU6gebfY/F3U6WdboxSwzjZc4tElpG7Oi6lzeuhuF9YdgOwwsppU3fYsueaQQd\r\n" +
                "A6Rt0EBYFsatV0HbkZZBRTeNvwQDT8EcLZU2f9LTF02Iiw6PPUv/VvbiOUtQajv2C62ZwGu3ay4z\r\n" +
                "k9lOFC0eX16+Ig0T0qM1qXFf1f5VVFmbKL2JgZRsqzhgUPaqO8m7VE6uFNVOinXFV7wjYBie4Tui\r\n" +
                "lXAzzkU0Z8lhgwoGRastmxYXH+tCBoL/omURW0+fNAQNzCVq/kglpNGhwA8t5rLYS2qMeS1/dial\r\n" +
                "lhVvVS1jIT5FrknIIF+VceKNwE9sS5yQcl84lK11SVX29Xb+APXjr6Ee2w++okxgUjaCfVCKsW08\r\n" +
                "viPSopg8PJyEjcCPPlv1RPZe/XqlQiozTZ1mVYM8YgYznilFM7LyVD7m2rbrOs5Ligm3KWrj0DWE\r\n" +
                "oxO5lQ/anDA6M1js6AuBRYLYVBFMgA//A6Q9r61AvD0OjxttfHdpqGjJnWSEep/qf3LrwfA7Ua3h\r\n" +
                "x1GQpWFUq9br2XY98PO6D3kn9qI4jpPNhuWotEBZPSQ0gijfF7YVxJRepSJiLBBfBumPAduT3LKN\r\n" +
                "3efGrkOdg5lN7uXUDThxDkrKYg6ZiboJ5A2uAYf1nT9Dvv2l1tUG8ApZhIeclkL9Roa7waUwnImP\r\n" +
                "7pZ4Q6wQxtj+jcuzDX0t0/XzUM+chyZa8SFJiPWNeO0FgCP0vBc56+thwxNNb/9i1AilR435EfyO\r\n" +
                "zMKvqGVeA9v+DXjQPFRg79KegKfj06oGRQHvShNsXRkFxkmMFIUehoPeRLC9ck5ZGqf/SUqKqcrM\r\n" +
                "lKukaEhTRf1YhsEwjBqZ6SyOqSPN2mZq6y8HUexdJtJ0BeWskedyNfZNqAUqlhv4zM1guqBmi/OT\r\n" +
                "doVEYG+ODIjNiTnrhA2oOW8EQ9BvU3ZHNnNM5rCexSCBWu4ZGsKTSsKuzpxvGlZWc8SDoEXe9yyD\r\n" +
                "NCGO66/Aa9QweZBKiMl9bprghPtNz5cuNXjuWwZ1VGSWmzpvxsl6Qzd4Rzciv2L5maFiFAdd39Z+\r\n" +
                "oSUg3+WpJScxuhbaLEVxghB2K4l4Vyy/cqtpu+XrNAj1ec+v0SOq4vHakCgceOhA0dDVRY3ZQWKT\r\n" +
                "78OdvwYlvZBQ4nwJOt+MBGe8wW3K5GXoR8F7GtTOIgb+b8LtXtP+CdX3SFuH2HYC9K6uMS2+35jw\r\n" +
                "8DWBCiV43xVFnjxRiiIB7/x7ONMb+2JtCfz+LESwgwfS9MCsMMx4u/Fh5qaS2VSGh3Q7/ODZ41Vq\r\n" +
                "B5zRV1zdMO4Bec7zbkf4kd+fJfPQHZqG7UDK+zzjme8QJi7pUZhusWxu375vzxBWg4yjuBE7f4Xs\r\n" +
                "/1PIBatgy9tFJTstPndD9vRlV1Q7+ERS9y8q814XnolqZDzo4SrVSbPPDeDFm5JuGK2tBcHbjIDP\r\n" +
                "diFM0yi1bcv6ohMGURjoyTNtWtmq96pxAxLLz6LBbBIOh9EXeCjrZNYyE7OfTwKG248MQ/gmrfYh\r\n" +
                "rrPMfEDpxt2s65ZwLTuoQp78hpqoD+OQ507xRvxLUaWf0C4UeqJ3It+un6KjTrh7Y8ic8FwOnEVM\r\n" +
                "BF9enhRHD3FKW11Zq4leJ/IzYkejAa9ExG0vVBmUMDGv9oJaNexFWcUWNG41m+GziaLT0Kau2PCC\r\n" +
                "oOe6LS+PTLoGV2/pPhc2o/kcLCB8JqPMhaAI9EhfG0lC7M5EGH6VkBM670ivJTSNQL79o/Z37efa\r\n" +
                "Ye0h7QPac9rz2icmNUs54Zatc2qUWJwUzwsMOxPloxSKVUm5HAaxvGlll5ZSHohUVRF48uHxcXoE\r\n" +
                "HiQskIQ6IfeCo8DDCIKARaifQzlhUpfbZGRKRm2Qo+s+7PleuMB9D55f1pyuh2Ngu2tWrpiqcxo6\r\n" +
                "SybkxOu63WgwltREhcn3jV7y4BAiA8Y+yeBpYsI7yIG6gLtBGMZ+Ess+uAUlgU3gYEIcBwpsQnhT\r\n" +
                "WGYOTgP/IPTlooiuXI28RU/GXgDvBEheVRcu6TqVyvvb/UHoQDRodau6/mieO1JQ2/Y4tTVdg0xV\r\n" +
                "+dTF5R98/Kq3cVtzFUHTvnv/+duq/eHjz1zbeX3nt9qOdgu6HOxTTVC4tXNL6+vazuvwvaNNlu3+\r\n" +
                "jAeBkBefH93FAUAbYAEEgCAWAGv4bSESbGOACWCADFsHYCDdBrgAirQlBMP1DM+ycNwtrZEAXuov\r\n" +
                "41y3RHdwb4Fteb1CBaDjXab3WcL5HM/QkO/yvtO9XWzVvEPIowdYwf0kzp2uqwN8vMt0TMeWIW0V\r\n" +
                "16t9JOghAbwdsAnoAIaAa4ADuIaV+FEIAfN4Jlfz94yz6Rjqx32T8Sn/e8fKcGDv84BLgMcBjwGe\r\n" +
                "BCwjzgAOAlYBK4gDgBv4vYG8TL/V2FHsb+Eea4AlHFvG9hjufT/K5WHsK/pJwFN4r2PYnsPxbZTh\r\n" +
                "AzhP0c7i/lt4ljrjNOAw7n2qNEfR20g/VjpvFdceLe11Bs+6D+lTOWzi+BxghLQlnK/oM8jXOTzn\r\n" +
                "EvJ3EnlZQto7EY+U+HsHfiv6BZx3BrGKfEzloWQ8Rpo6792Ai0g/j3gU91TBoQa4DPiJPrHHT2N7\r\n" +
                "L6AKCFGXAeAJQB11pegp0hVtH+BZ1IGS2TzOmyvtM0L5K1kr+7+J/ChZbpTk2UdsoC66SPexXd4D\r\n" +
                "dXaO91TzT2A/Aizg+E0c7+H5Y7zLacQiQtnYfuSpiRiWkOMeQ+Qtxzl9/J6ep/Tt3CXY/xgXUf9K\r\n" +
                "xyeRv1Ooe9WeQRkM7wJKhtPUovz7+l30b5S+y+PleW9lfC/2zv9/hLKjD2H71J6xt0q/G9zc862X\r\n" +
                "8N/YUoLt7/SJvysom1D+3NInNq/GVX2icpLA7733kwjlJ8r3TmK/jWtUbFB+Vce9lV/4eEYTz5R4\r\n" +
                "RmsPutiqvWZK6OCYOn8W+8oHhvidAZ7Hs1UbYLu/1H4E76XGYqSvoowV75/BsaeRJxUT7kEEJTTw\r\n" +
                "jtPcqGJEDdeoOHQE77qB98lx3SLutY5y66IO/s14lfTYdVThajy8W1V3fEO/1x5aHmSLJNht0zEG\r\n" +
                "ZSKRHANOBEYgEBtWsIuQkADDgoglfyAbFhEbNmyyCChS/hV7BKe+852quq/bUnRV99atOnXqzMNr\r\n" +
                "xPOAMk1x7z3Kakf+rla83qF87nPtlLhuHZR4a3ncapFbByW23SG+ZA93q7Obalzj3VvOhwO1hRPC\r\n" +
                "3iDue1w7Jh3p+xZ5Sjw+4fyY8jQY07PxlHLb3w807z2lDO5zfsrvtyqZv0na75OnuxwPSdsN0m61\r\n" +
                "yQ3y8Tr/7x2UuHiTsrpLXm4Sb5LTO9TZXfKR9PmrA62jLG98g2dSnj06KL6V/t/nOCEfN8n/NY6j\r\n" +
                "atwjX5bTEv43SMdb/H9OmhIPzyjDR1xL/98mrQn2O+TrPvGaXb5LvA/J2zdJ3x3+nxDH6UGx/6SP\r\n" +
                "D6r/E37fI656/TWOVymvR/x/hbSccn5COl/wP9F36SuOCxzJj1OcSTFlx7GmPK9Rjlve8y8Zn8n4\r\n" +
                "VMaXMr6Q8bmMT3gmjY9SbS3f3u3cXXdP6vuV1OqtO5IOopfnilTdnVTWraxO8h9kFmTeybuRoU8n\r\n" +
                "vcNCMPzGvZBViarSOwzyXJankR39NtIBRIwjdxV93JE8Up+7X8rJj2T83P3e/dn90H3snst45t52\r\n" +
                "P3BvuMfuqfue+5l7xf3Ifdc9dCfuHTnxuvQkNwTb79zf3HV3X+a33U0ZHwrEr6XvOpXvqXCU3g/k\r\n" +
                "uS9QCeKu3H2KtYeC4bF0Vo/k1sfyfixdySNZ1/EYMCfSjTzA/1/dm3L+udD7T/ee+757IlQ9cT8R\r\n" +
                "ij4UGn4sfy/ktmeylvZ+Ku+nmJ0K7Wn2sfuje1ek96rQfCxY/iRYX7hD4ftT9w/p0fTZ8dnIcyxy\r\n" +
                "3sj9a/c5JPpv9wf3mfvSfeE+gW4kSsmJwf3C/Qf/g8Ckp3cjpJ++SXsJwwCtDtBghMbSXi87Sbsr\r\n" +
                "eRKGdF+Abn8rdAfh94FQsZJzyRpuwQp60cIt4BlxZyu03ZH1Q2BSOiJwbwkx4J4eVtTiXCffY5HG\r\n" +
                "WtYSzAQMicq1aCfd0YkMeoG6BmsZQF+yv2SdR3ImghvlIvGT7G0USqL7QHC1srrkiQhqWpknWUwC\r\n" +
                "2eLxkFIHSnWsBM+Ak43MDcMoI+Hz8j/KEyCNhKEXuMTPFjcojg6SiqC9pe8EwJdZCxoGeFDSV5Of\r\n" +
                "NL8icEPe8fLn8U449dtQz567ieslzocZplBhbvI9CxlT9d/Abpq9J1YQfna+4Z0Nby4wFysa9P5I\r\n" +
                "Hsqqx/0NrKw+HTK2jqvlvoBTfo+fmq40Luf18Qw3jbtUzULF2Vn513K7OKPDaCnz+Y7J7eWP0Zqe\r\n" +
                "CzNZayytoQI8ay6Js3o6b20BGQenmgp7e/v49qU6t4Eip7P7IY9QrRRt1jTGPTnX/M0l4+FTRtnL\r\n" +
                "Oa51pRR0vOk86P272oyleNJ5El18JYmfhTaOz573GTJZdS39Cy/BWvjcv//llLx8/+za+do1Gi86\r\n" +
                "tahFZTtGvfGXom+KkiMjcotY5SUneGQEjVA9vXhAfFM/Vb7azN8Cp7aycshouZSzG1Qcmq163DFA\r\n" +
                "24H/a0S0K5IZOnlfk/dVROEjaGEnK8eS+a8j/o+gJAr+RHMgvpaZpEWMX0j8VytuGZM8bdass620\r\n" +
                "EiorMgv02Sss5hUrm+StHhoo20B7aTOOopf0nijrNE81wlqovyI8BmTISTgKsvIXcBKQHT2hz2JL\r\n" +
                "0T7BeeHRNN26Jt9l1tBmP55wYlFFTcsZBbNGG50vcUsDvVvE7F2T+St09bM/xVb8cMjnJvz3rsQE\r\n" +
                "hb7MXTvb7dmvz/a5H/kactYwI0dg77nTI1eP4LrQ4lGh9BWtXbaKpqLdwwPO+pxWF8bxglgWOafr\r\n" +
                "3mp2skTGfS/19KIG9dZY8T7OOOXsf/91B/I9kCo6ohoKYkEbsaYNqj/9JsvaiCUlf1uJZa1Qf+5k\r\n" +
                "ZYkqcoX3Iaqj6+JdCTatpFnyrSFbzcRv8uZIiwvYnQR2ovc2Uql7eOBt6OAIlW/A922pzwOrn15W\r\n" +
                "ExdLWFfDmGKVRG2XamMXMt/9njSLVL5WWVqUv3ks3M+bHhBF65GaT8+EqOJzHEgnW1LWI150rAc9\r\n" +
                "5lq1N8LTCIgmV3xfx9lDyHIpaxrPVujDVrSS66ipJ+huBZkv8e7Ql0VEyxUwpsiwZaab5NwSNn0M\r\n" +
                "XU7QXIJNdG1xR7KiNargDSxgg/0No+QAnx+xf4gYvYZeljib9HsdPcYSvE/IAT2sY0JMNT0OqHwH\r\n" +
                "0fBScATYxQSbW6HCTpazYcwfIC+VbsRNLWJ/gF0umWu0v5hQsXfgIUl2Q2ms0AdN8JJD5qcJNw7w\r\n" +
                "oRZdgsb9HjIcQOlA7SxBW8cbJ+BeAU+i6xDdUZLdFm+VwFL01UOHK8SMFp2NZpjIzisg62hHNICi\r\n" +
                "CdIawWUPDlagtQMvPXhdUwdr8LXEfWt42QhP2wGyBcwK+fLQWc7q2ZkMkLFa7UDLCaCyo0V1sKoG\r\n" +
                "/E64x+PGARADpDpB0hF0KY4RswHWpxlVdXQAmY25Qu5pqQqhHu3Zv6q8OvAfQZ+HxQXahWXqEU+6\r\n" +
                "fw1e1Iq074uw1x5wHvEkgKu16KgDP6prtRD1XNWph1YDtTDCDkdIeMnYNYCmETdM1NWIGDwAZwed\r\n" +
                "a7Q7hKV6rKlMJ9DVZQoG6H+gHWj/2GUtDbCsgfLts6T1XE8btf2OZ4xz60gH1k6KoSdGw2Z47GRP\r\n" +
                "KspfTyoUYqK/2QgZ94gx5J0+U9/lO3pIpMwNLtAOC5Wx4l+hJv619IQy+mpFqbd5m/F1/LMTHSzZ\r\n" +
                "1o3nQKoNTncjT7SIbD190rQ1l6vFI+PB4v785h2eI+S5I4nTV1G7pr8r3EvruqpwCWpX7af1LYae\r\n" +
                "2/G9k1M22wJilyHL/w7xe5vXAuKShwRKz6j1qkaH6Kz28vCvsFeT1PVGZF1rPVZgpReIKTC/9qiC\r\n" +
                "tG5ZVXVOwRZy1VbyslbOl3NNHZh3G2hqcHXWHpx1KVrFN7xdawft0IyayDrcqrgeFJb6IuT5nEKT\r\n" +
                "VZHPeV1Bua2byews7CLvFt7P7zCs2hwp8zHrrs8y0XEJa6UyaTKukCVScBd5hgzpc4/WzWRiNATc\r\n" +
                "GbM21Go6ytNzbvJbZgweVeFAuQ35lKdsIzEMhCn3js7nU4pZdTHSzozSnjbWZkkG5J7IyizQKoom\r\n" +
                "I+7zjAyaS3zmzm5SWqZKsj0zSZG+Rv8GcB0gWu4N+TbNTrHCHklXYHRXTAPqgJZ5LlJPgXrpUFuU\r\n" +
                "TqNhrxUYkZSrDpnbrG7Lb0sv16zhnXWjyoPRPJJufbfwWPWJlrpqs02Wni9WlhRgRQMzj68s0Tq3\r\n" +
                "1lnHZzHH5KUUrYg3ZD+zPqslnmHmV8VLPa1SPbGll1u30lXWVluBcVOsvMt+EDL+lrZTdyJ+D6ah\r\n" +
                "dtvcdXhG3P1oYNK0zmTe9VmE8jluqTUXyzbftRssXs87oY7ytwhrGqp5KzqraYgVzXp7pPWbxIzm\r\n" +
                "mO83ifSUeckVE62tllrNq54daCEe/lb2zztV6yDk0c3swqympnautaKRmOVsEimxss/6CK5ktwQx\r\n" +
                "EVOXraKlt8YsY5+11bAC3OfDKOko5262U+ixyFYwhCq2me01mRefcQwVtnZGT7k9wsI68r3I2Op4\r\n" +
                "7HMUnD99/g7utlQdLeLzGvYWqd/OaczXiDyi1o6Uh9lxxM2WH7QiUzvXuNqh1zlCrd2yQzKtaF0W\r\n" +
                "UM2v0DEsZRwKvpXT3iugC/SoYD2ruhRtjhiFA6w7oH/cUKfqMy36g+i0L5rYibTkbUBPFliNBoHZ\r\n" +
                "4qZQUdawsrWI24GShlktMrpoBG7ZXbTsmexcw04jOsv5Gkl7esEaEgqQViTOVCUuXOqPzKKWlGrJ\r\n" +
                "017qLJuXiKoWbxVdzFZolYNmTrMno75ldOhy9DLoulYpvlby4calDvQKooDluh69VEf+tZbfwQbG\r\n" +
                "3IVsIMlYVfkBmvKCS2nT2jewStddrdeT9Jf/57vaeh43bugUaFZz00iyv+wlmw3StAWKPvah//+n\r\n" +
                "VSJ5eDiyGwi2ZWmGHN4OD5PymjXpzIr5DQhdJH46bW4SbVRKEdbxWf6P09Pd/IoodOvFegZ9shvS\r\n" +
                "amYNz0L1tNq+yP0jVXu2W8TW9DfTPSyLm7ztpvNa9ZvYsMjpv8j5tkSusaUSMqSKvm4Z9d2wa3g1\r\n" +
                "dotws+/m9+gNwzMIjKKYH8Fc2JnQQ1rQDxbSxIJma7JUsSLHLrFSnz2MJ4A3FXlfHZ/zGefd5a0J\r\n" +
                "nVKj8pQa14rbDS04WT59goTMbHmgvVBnV43ILn7BLDtMx/X/IdnBKVP3P9IfktvNa+WzYfTvglW7\r\n" +
                "sbrLut/PrP3jjHMxbNKOeKHBD5tmFQkP4ZpNLHlYlwKP3CQrrs83Ode3c+1D7P5uVly+PKx+duli\r\n" +
                "xSpts7l2k8+HrcA1JGNU/i7ZvFlN6Ntsvt3kjLvZf3i1qlTFzyxVrF7c/Mz6rfWlVfqrxWhLkQ0V\r\n" +
                "j3P3TCPSaJVj3sDqGYnAojCD1PCeeRjnADKNWRsZwSxn1hZX818Lp2eVcIYkJxl2bdL9mnUbnQjU\r\n" +
                "o/DRP85/v8v7a4b4IrsOybKn5anGvnuP3STni2XwsFzSLDhM05aKP1tNhuKtonQR9LnO+T1pbxwm\r\n" +
                "ibFs6V/GQbUOthR5aLfu8feADpiB+s1rNcXJqBguEl+QHYxk3EM0wzSASFSXn11/jBq6R7UO1wyL\r\n" +
                "VpM5nMW16QQ8Xbc+UI3/aA5rLIB0m50FO5pgPSdalTJcS7e+pD4Fx1Isb9YTYTcmFD01tBKjZ28o\r\n" +
                "f1FsHh4PnE7/7XbSIdiidh6mATFd3f/VeBp6I3ppnTxUhT/Rj3XSzaxYg79rAuuJvSYyf8wlmKVw\r\n" +
                "HkxExZhLFcyNE5hmKdCgu1zytuL9CF1vSWA8WIFczYlZy9yCnog17KucxHKoGp4aLBrcn3MA7GsB\r\n" +
                "Z3JAQFpQnYdz8rj2/TUR+fBOme/1bPWTcAaEluo7OZMU66otSCUXjciJWQjnwt5sfuH0RuQnpjeX\r\n" +
                "W60XkDk365nDzwj716A/+zkQychkkWkR73mWljhbxR7AqYo8W6NIxoJ8RG4gxpw8u11PQYZL2maM\r\n" +
                "7DDs1U79sLXNMQUeUazpFrctZEjxvCA21wRWHeOkp9zSf4VdZcuJyP/hH1Y/874490SFrLYSkxVX\r\n" +
                "d6vYnkqKlcPoQVN236PeUMe46/K+uWbmmnqWz+n95ruvf4vpGB577dfdz1R8vXLi6v5strYlYE6s\r\n" +
                "/3rTizv4gpYzJzlZ4bv5+WMMsmmOSMg8I0ohP0s44cxj2JtXO8diK2OWg3PRIt71cPYZiyif+R+9\r\n" +
                "HDldtv17ij6oFkn6brVnJWFGpebsHqXekoCz1bCCnow8MvJRIli22MZKr76euN2m3xhBaNZ8nD3L\r\n" +
                "qHAna4xv2RtwQmIoKzhbFs9drPvJY1dlzwR7wkmZw3N8YnXFVcg/VASnTKATUYg4hEurenupF3q6\r\n" +
                "BY+xe+Zwh+42+4mdnujb/L1WPjt0MTSBlsX9Sr6B+orZO1c81rTEWmve5a/3y82+mFlgKNEm6KQ+\r\n" +
                "voNfcpCP65HQWzRaF1Ic0hmwfheva+eIXqHGyJXg0csvm3Tg7NnKHAJv6T4zKDof1g1GIhNnjK49\r\n" +
                "q/Db1XzTvVb0t4qkIpNmlu/IHlZ7U60CPqbIZd+zycqe6CPoByqww5OXZevG9+vqjZ9lJvrP+f0j\r\n" +
                "/ft88hS/KxvZzdJn+nZ+rqmunL+rdPmHIdhXWbGds9YhNl56f5z3n08bdrH5muZ2YzxFupL25WIT\r\n" +
                "wxC/1VPih00Lj1NDkRhtNutpde2GDc3my8MmuiHR0vXNfNTEq18lKpgaN/HssGgUm2R0hsTU0EWG\r\n" +
                "zqGrR6ac9ijSfNikulndPB2BPiy+X2yGXWWfrjvEr2qNTguH3F3fj/M6ROYua3aLdbf1h2XfLv+v\r\n" +
                "We1nkb7as2ZZuVo+PUTuntBdquy5uFcR6deTD/NzEc3KyR6OF1pt7Yzjz6c1j1PqL+avQ6pwO59/\r\n" +
                "k1PFjpSF+7HSiGea/1/OyjvkLGC1moMXe/lLUswhHwJ/RkUz51kP4+3zbLLWW/3cq2CVVbvf41oE\r\n" +
                "IRbzDdEmXp/k/eo7UOd3jfGqvnZ/8zzKic/W2++FX0+RwNUtSFuClc3OuIW176/Zi8N1vfPbeNmt\r\n" +
                "8frJ/5fpDpjX7RSLy+S6/UXm7I9ZapXcpd365nAuuQe5ZIOvsqPE64N995wiF9Kdq0fj4W+x5+PN\r\n" +
                "ee8XeA7/owrfrf6U2GMYi+L+GVKHkRHg/Tb5cNx6wjJlPeKI3fd3OEER5FfL58yj5Gj98lbmZt08\r\n" +
                "yp/9VW7yYBvt/rNaO14kXZeiKk4UM6Dc5D3DBIG9S1g5fP0y7Zv9QRuK5WVJ91NF5vVaETXd7eQ/\r\n" +
                "xO+VJXAFGH30yP/Dgiu6Dz9Ve5HIzIuy7mjwLirljd2vuRNP907KOxx+Hz1dXYSRvJM+vAI4MbyX\r\n" +
                "+u737scxWVJvv0PWRbSiP1ZfN8uuIcuh6Z19ZXr7Gtf2Zp+eiRa97opv33W0ciJSjPprXJS1L3YP\r\n" +
                "1Md13V9vP0kclBd+nHdfZd1hvK673M3mjyVxks0ynymm6CQ4kk4mzSzAfBujt9hcBGag9xcnehqm\r\n" +
                "r8b2s80c0Z4ijKd67eu8E7P7vT///LpH/v784kwfbslI2ebQnNDfMb0gK7qxQ2WaV9V+MZb8XXy5\r\n" +
                "Ctsbwm8Zb86tWufsRf3lbNHmHmSg/+r19eSP34SzbuJf5bybZzw8nYOsFnpEc3k6jV2ZcFhUlKcX\r\n" +
                "ebPb1DAkC/TpZf8hPtDptcoO9VSxSayIrVVO1o3p61zXTcYmGapvu/02Y6mr2LHa5KBezzY/DstO\r\n" +
                "nQxWk3eYFD2DcuxrMsL7ITKb/Db5gL8Xn0932VV8yukJfH0zX6hfV+8q3WwvifPXajOIWgr9m8Wo\r\n" +
                "mJVZJkHlEcOyvcn3LvFQHc0qAzZpZjRbP8z+zawG3laR0OQzPM5d9FSxotv64SeHtbvETPUzHk/r\r\n" +
                "pNXyqCewsmqRqJYXq51gTdklN3vSEieg6ner3Q2rQ2RPs53FtKmM4tqAVcjYOu1H7y+uFVZDZrN3\r\n" +
                "mJegtaYSLuKUegh8A9qqcwzYe/+FD/TJapqzx6D66uYRLOFNMV9WOx2iDy8M212Drmr7kM9EtCy5\r\n" +
                "mK0D1Om00J0nT+BJ8ZXsjpHLMDuAP7SKq2fPNOlZiFZNxL/IaaI/s5+hhuc1veZE9tNU8xZ6F6yt\r\n" +
                "k7e1Pza3BHgWo9HM3u476At0LPi52v8++SiemKw05ho93IJsrkeNr+I95jfk08f6Ye+Keu7xzCGn\r\n" +
                "sGa5RQsSl4R+NvfwOVr04L0To3a6SwcKs/9Sbzefxs5dJqms0HLTw2zR2mthnUZ6TN7Aiegt7QPE\r\n" +
                "lIhc8G+sHmQm0IFrqu+sZivzJWZtXFl8B34haZ6T5syb4x89wNNH1hFjTESNz5vbU9w+emyuBbxt\r\n" +
                "KdYWdLZUrCLowe6S8OwIUkua2aLKVk3gp7p2GFOK1UCb7nnPqOH/mlAHXM+sHi85MNdjrKQsjAGx\r\n" +
                "ByplQ+ue5thwT8TUWO/0CjPhznWVy3zc7H/lvPoU+Lyk6IOIi5GPcs4Ae4x1xtmrhgqLnLUE5one\r\n" +
                "wzqbJb2iP7sLvBjRZu6arDr0Sz03eAoyuSSy+rsdJZysTadhT5wRiniNU2ymkVmWJzkzes3r9ExL\r\n" +
                "OEkP9UKkuOP43MvmrqJ7Vsu/PLGfr8b3Nrcq+z2uYfWJzGuTFr27cn5P7OXt5I1z/4gdLuJdD7Zk\r\n" +
                "46mxO8VqJdqxftYUa0+rdfb44qhZ0z8T65yIhjjODCF6M/u+lmYMveMK62quPGBF7MdV1pKLRMYZ\r\n" +
                "0YESFl83EntOneyprmlGc/aPqIFcAzkW+616prvV9Bgrf7HKWkKUMb8svuqdZxZDl2hjDefROemK\r\n" +
                "8C7TEXzcEm2jz+Ic9IpkrIWamC8R6fjkp7dviRj9Jjtq67f/ivzZvXfXxx6NGEHb5b/jJQdairHL\r\n" +
                "weI9VY8TuztPRCxgDnykX3x/CXZB02zh7FPUK073KQEvZp9d1xq+OSFE/ooY5jPq1BR7B9b1xO6C\r\n" +
                "Ou3+pvva6Osa9M1ojLpafR99G+eEOcLxLPo740UTXL3kPkL1qaQ9Fasp7SoXDz28S4EPL14Jw96t\r\n" +
                "NtW1oJescEtgZNnWs2Mrqj7TZ3n/SD/Sr+m39D+6q0XHkdwGCsnZ3Xp2257d2ewlOVyABMj9/wem\r\n" +
                "RVaRlOcCYzxutUTxWSz+dn3/vP6GyOvy9rx2DZE2Lp1+FRuO6zNn1+f1N65fR/pMROuf1/OszY/r\r\n" +
                "tib3z+73U3rKtPGE3XpHlnseQCzV63ndeMi+Jh89N+R9M4Ql12uI/Fqda37EemJmcwp8R1vHjmLP\r\n" +
                "PlEWwTbPBe9UxT5ePTsyKaLvZtnHU2vtbokd3/vOZozLK1ZPbJjLit2Sk2e3V7XPZyXclRNnsugN\r\n" +
                "z23aVs1673Vl0YS554zfsfi9g21vzzE+JezRCvnPlR3nlZtFsuPHlR1PyXvl94dk28zPE2tVnpQV\r\n" +
                "Kq9RnjNX9RyrRrOrJ+Kaa0BWTjyIsVIJvrPIUzdfuj0xN1STCj+X8D+nPy4LH6ixKf1E1mtlKRd7\r\n" +
                "gpUNsUprocO6KrZwAuLTkAqZv7+LX0ZS3lbw9kN0OOwmVtqQu5poUtM3WFflLPdW3NdwQ4bnTzBK\r\n" +
                "5USHVH4FXz3h74Y9h8jS38SBJjZo3Z/Y25F9A7F42M4mkVQ9lRfr6UMkDHwq5HXg6wtYVuHLDl8S\r\n" +
                "Xbppw0x5SJ4N8W6Wkx02NmhfzaqCuESmWlCfD7nlgbqd3ZE74jzlNUvU+H+oxtq6L1jjlc46VLl3\r\n" +
                "q0Tez2pb+d+O7Fy7ovP1rww8alYW/TN6Vqyukpwp1hRtctTzeUuRry2eqsG+teepXfyLCLROd9V8\r\n" +
                "5+x5W6zaFo/wrSNX9FhBTUVcVsZIhCCa3I3blmBptUiT6zFj2HFiFtwNoUeKc8uaHWsXYlYQ9ddO\r\n" +
                "x9XIxefvj2BPjJ6fuKccnkvwN/tkTUTOnthLlFfmsDdy2Hv6q2ngEZjVd7e7iknOyLF379Muz7B1\r\n" +
                "DlqzJ/pXT//tYjqTh5yQ1+Fn71rMZHrGebpzDO4+QlS9ikrYF/ttTezE1XLgzzn02m32RbbPXsz3\r\n" +
                "r9w6zpjOJRgL19czxhEtL6dreLvGwXOOK5vNBdTXvaXs8JUK+vgumcOe0683VTQlI1HMnt3zmyBr\r\n" +
                "EXz+ML1P6RfKxdnxG3yhONeB/mSgxAbtYNoNPuCTx5UT2tMOYddHyPpqGKDf3aQU6Uj0xpnIAfuf\r\n" +
                "Rr+ASxDHosxsGZjDk2p8JMeVbhGtS02Sofi9zkK0Z9+DJpGlul96kFCg7906RuwVw7JOu/NXPI29\r\n" +
                "hLbr3mx4oZE8E7GQJ2hJS2TnfI555Vnawi5KpRbO1uIcQA118upW9Ue4LXLt1ZJ42y4I4s/ZmGBd\r\n" +
                "TuXl9mhTrMcadvH3MO2JRM20d000GyrqoWFmiFrRJuJzlcw/rX9qr6tvdq4I6/3CbegpeqVa7Y0U\r\n" +
                "kbwl780zE1+w7pdELNdZqMhbZzeb3bPZ313+t8QOy6x5mL7VOkoWG+ME4gjmPvJewZjp+dv1/wZr\r\n" +
                "yRB2dO04eWnN3S9rHH3JNP4iOjZDz4llQ2q6wz81YIX6swFzWFsVMjKeOrw+16Y8RauJoJ+Q1BGh\r\n" +
                "D4v7jlxlfDmF7TZ/6Mkmd7Ja/GyzXKzijSfYObunRiBOWZTEDvCQWPAeRq3aXbEDe3+P06hzoJp2\r\n" +
                "85Rq92knGZvInXbzmFtOZI3ZrLn8a2LfV7/XoJtamjE38Gnm2IlOXpP7zKVXaFoNLyuwa+UnH1af\r\n" +
                "WbrRjtqhpZEPRu7rk21f8tpPtKuTOnPezUdnUh6kCMgKor1N5MVYtrSyfDJV7TNdfPO6Vr7JLDgw\r\n" +
                "3x22Q885N87SRR1FmXOfiTxqN71p+2H13ZJzlAO94EjkEFHekHcjZePXNbxRLFMfHpbDPe3J+xv7\r\n" +
                "/4nzIxFNKnKB3db7YXnT3uPiHnQLPW9K2lFFEbl6IgZpTLbltEtYuaBXaBVrb/AVY9GvrOvhVBdP\r\n" +
                "kJd+v95+Xt/ntfYhyH5cv5+Ct8f15hQ21dNPqYLp9fPaN+TEuPY1OTN9/HnlxEtk73L6dX13oF0X\r\n" +
                "hDhlZwOyfEsZssmpHuJhrZqZW4p+3qlmbDosbhLrHupRV7tV3lrf2msO+LQnot4O3Bsm5xH8PFGA\r\n" +
                "XYo7mA8dWu+SMbvES7nQjkqLXE35qzPiLp4cggjv8Xz/lC8r64mBbkO89ixh3tTlxLl0DU5JR3Im\r\n" +
                "Nb+/icURawo8Gev2FDv0diL7ITYeNn2d8N+Mxd/t1u+GOuq//4r0f7/5w6eSgrrwNx1yvHL08yHf\r\n" +
                "/zQ5v6ffwJD1e4OUu50Yy/mvN8/ZpoV3j+SVSo/dgSXqm6f5U7uwrjbDQs/JYp2Ck8CK71vgF67Z\r\n" +
                "TW4nQvwiv27YpbxGJ7bb9X0LljKOt/TjWu2QscmpDTI3k73Zs3qL993wfV+86B/vIbzjDjnz/wsW\r\n" +
                "/MCuOzTdRVf69RaioKzNtZj7+WuDJpvYusndG6RwgqHVm2WSY+sac66z+00Jv1/Y9pSJdCLkH5fe\r\n" +
                "Bd3wkK77RFwPxLqlfwgqPq9zH1d2z3p7JOfZc9J9oEKYIbzvFE1fwv28apuhTZwkyY6cXTmH0S6c\r\n" +
                "zX4ysg15R7nvWELGt1bUO1Obz/+6PKGIdsq52TkU96fFiv06T2bpIorph3SaAx2523dNnGmLIEa/\r\n" +
                "JAzUCrnMEImK8VP6Kb4mwy2IrfaYAc5TRTONzoHz7dJxgGnr+2GYPMDGTpHxFAk6k53oZl12H9h7\r\n" +
                "osMdIumQ+3dYrnfVpcOwexSxTzvj7AMnOtUJXlBTWWJdhIGPlJd1diNOCeS9PEG2TT6T8ZvdsYif\r\n" +
                "smm7W1Y6Z28S1w5/crKt2O9s9xDtfeahFs6tdsw9GRZm9OSCCHariIpfGnFGW7NlyNv5eaYd05bq\r\n" +
                "MiTCDd1wevlhUeZ0M988xBcdXom67eAPbsHM7lfilNdl/8zjVyKaa5UOdH524Aye2hPnEb+nw56C\r\n" +
                "KSfDygPRKmLbiagM5NtTzuiehj7bLz1u4MW7eIaZ0uDhnsiifR7bIUWrd8DPp+i8J/ZqcuQmVc1V\r\n" +
                "rYWH5PyQiD/kpgOxL8gt9r+HrFdMChkZ/Ew63Z7ImRP40SXTtLuSK52wlLlzGPtTPK/mP3qZU1C1\r\n" +
                "mOtKSdnW+duzVZ+2tEMenzlrxvPR1mL7yWyJZhn4c2C1JWLZDh7gPJR+a7ipB7mc+ThDVUSwmo28\r\n" +
                "qwAf2S04kTmzcxTa4LUCXHOcLagL1b5aDbO30CMnMqwCCZTZNtPS/3TSdbakN3R7z7zX3y/bV4Ez\r\n" +
                "N8P4YtZoPTaTWBJnuYy874bA9Bx3dKsF5gOzhrlCuTnI74lcPpu/2G1z4uyiNdNSnF0875ixGX1j\r\n" +
                "R1bkwLYVf4iWfl+3fkK9ud8z0eeePZFZUttuOTogv5kEz+fIMThL1PCeWbmbDu4nR/vvqGzfWcKO\r\n" +
                "9x6WzUfva9lyIZsnebakyExofzGNyiK7LDvonwJEfq9P764+xantHTY/LG8zstc9mU0jPrtO1XaT\r\n" +
                "b9HPtKcI5rk02l8tP70yXOMSPO42kBd4pBmHZnrvdsfK8Cr4MjPc7/FI1bDqteIVxwhVO8H+E+Oz\r\n" +
                "h/u75WdGzsf8qEHWPH9HZrNCfEKqQbcRdNuSZ40zbI/gaf99KqDP3P9eLc6qIlay3yrKzOmkm5e8\r\n" +
                "TqKX6GlmA1mxa8tpgf2cZ5gdeVlnFMj1a7CGHl4nrPcVnaE289J2ndoSsbfibzOPaC6PtKOLeiZl\r\n" +
                "03y+GXgbI7FWt3poszpyb8XcrMjQ3fSLOnOf8tsnTm9Byly7mQU+/Widu/e3JU/3oMsm9x/Bmw/c\r\n" +
                "vVZV1FA5ht8ZvRVtjrdl2+W+JGLM9XuQ25Djm8km2u3hTt8fPZi/aLsHWap19LKe8JXVomfAOs3N\r\n" +
                "IbPh7O9EKmbNrJETsSInIvJWqylyc2VVyrs1BwcmAM60h9WgWnPg1OTVm+y5Ay28S6nm97QHD9/B\r\n" +
                "be4hJm6nenFqejffRN99iBeUwSo+neienBvJ2jinHulTmBFnFjLvjBlTeYNyADKmI7FXVOtlujKw\r\n" +
                "2zl8My/qyY679DZ2lQ780ElQmapGawCPubNBJ864OjMMSFP+0W0iq+C1FdNeSwWWkEGxxzazqcMC\r\n" +
                "t1HnvApE7maVnj/R83R95fzF/Dk1fybnmNW41YH/3tucC2WJQUXN6y0ev2Y8yd9E9rzJzSc8O5AB\r\n" +
                "zAyPTwH+aAS7SVznCp0+m7FDRl4ng+N/pFfJkuS4DeVhXBJFUmtmVvU27YNP/v8PNAk8LJRU3T3h\r\n" +
                "YEiiuGLHg5Ob2YOMZKVtgWYL0cYxoqjmJvgP15tFM0pEhSF1isehCag8AV/yPTP0M+KegjhZVNpz\r\n" +
                "EIxueZ/5ZH+IsGP2mvZszjIFz3FOyCpjiTKC3aUxFo6gzGo34dBws9ixxy8cjQR1jOo7SWUpSM4w\r\n" +
                "uPG2BKmYPKa2viAKsVXDHCOomYPF6xz6CtZyeLO4RdfNIYJiPm9BlJUKUCzOY2dZOwTJwot6d4ZF\r\n" +
                "JI2zgut9njGk09ewVhP4Zlnve/i7PuzDWU9l/CF1y0gRfIY8h+rPIgeOwwWRTLJnCkmp8tnWpMmZ\r\n" +
                "h/PCAq4PRT1ML89btJc6coQFCycLdCdyFAsvKpNRc4RUEqNy9aAM8aAosVTOxCdYfsLfFiZ4Iu/9\r\n" +
                "IOoKUbk4Xz8o2i4uq7YTH0qDWM4CDmelOeK0GVWnxNkJ67OTqMcsXqNmq1dp36HO2O27P9evXZFt\r\n" +
                "IyzFKi6Pn5LO5HDGQv40qfg8JjXvYwux2u1MpejQ0Gpf/yVEKrtJbvDYyag2L8knSfhqzldR/Qqr\r\n" +
                "OWzFEKwWOD93HukRaTqdLmv4luEy63Gl2LnpJXX3iVeafpKbNZQ5a0yQCM0SeAuM58W3J6DfQSXF\r\n" +
                "+IC9uq3iTOVju2AE4VqylulxVDRtuT66f0FFk54q+UVqCpvpLa2XutdG1FUZ1idzk1ItFZiPsubF\r\n" +
                "Qo+h6at/iv33+hL+Z1hgxirJmWMwlGORT+gSjkRPfb03O8yUgBYYwUiVK7SPilNbRC/IWalGwFHx\r\n" +
                "auykYZKelGLxQ3kLqpXsm9SuGD8YwhmRkS1Lis1ayyoB8/iiUTshc3uqRH6eLskm1+iR9CbO2WZx\r\n" +
                "5uNmXQk0Gd+LWjZL0CKHv3863cg2lR1t3N6Unggfmtwp8YZ+2R/dKT9RMXwAF3vMblGuSX8PgrqY\r\n" +
                "lne9ZQBaTGqta+CIabcWtaWRsmaznEiRUHJ0pn0ZWk6ENDlbsF6bVa8ak7J6r2E9QYMRdY1EkJHq\r\n" +
                "Ph8xRsV/KUhkj4TIfFS180RLW2B8YNY+BKnSRliyWKagnYmojqDnIEmswG0F8S3Dhwp6CXY7qxVl\r\n" +
                "8tVJ1yat5mbEu4zKg21lpSepTowPPm+GDRfIx6wiA+1n4JolSP0SoX+rt0zuS5CKlDmb0cvgpeEX\r\n" +
                "i68b0VaCRMxzzi7hnHWlFvJR02qBuwzq45zFd5+Hex85Z4DY7RMMe84Q5zF/zqS3MZIddI9hjHPN\r\n" +
                "cObQ572eXm+ZZ0Rgp5lsrRYwnNUjvdh9LcoaBd4XJE8NylMEp4OjP156Z4319Hps5neeaR1uZXHO\r\n" +
                "4bFDc94WBvcdOk4YuUz0lvuGW9narOdXYl5yewbtea1MJLUhTO7c1O0YwnjhwO72aG/UfbJ20D7L\r\n" +
                "wazAzu3XWZXZS7j3DNtrI0KJ4aWrrGQsnc7r5Xnnhf7k4TQTu//PbGpUWx4vu65W471mcOMiq6j2\r\n" +
                "0N/Xayo7KQ7BUKxpYVSvOUeXX7c7CV6bYY2zvs6R8t4LhxN3Z9n+efszzv6cf9/urNPmYjc6dDbw\r\n" +
                "ezq87q1n3nK/8zw+wGru18du5+B6/v7hhqrriY+KLlqeLTh3qRjtSe2B96u+X/RtvXd8ee6oT3vz\r\n" +
                "6oNGD9171Jy9u9ZGzm2id5s9dP5FfwdOf9L7g97S5N4XvgfGPU2y9qOO/gD9H/X5Up938HTQyAtc\r\n" +
                "tpV77X+h3c/wFfzzLS+afxBPL3C503q+ea6yO2j1Bgl81HsO3MRye68Ypt36jjv43Aet2WgfS/uo\r\n" +
                "6z7orhfd8KpnMxVrpWqlk3Zw0NY+IaHGT6lzfGKiHQso2MBNO2Oh+xo1BVLbwY3c84C+HnpWo6Tt\r\n" +
                "2On2jThZQF07eyWttdUFmn8ST2wL7VuIiplkMVJvI4Tn8ZtUdmOIinDFi9aTJQ+a9zm6MS58C4LU\r\n" +
                "eWwneW2EXw+qFhjJrnWsVA5WcJFrbyW6F7LIxuFcvw2HrrQv0eyDVqw0W0gqG+YbtwedsdHYDiTa\r\n" +
                "cO2OmUxnHOR1B9G2UZW31acQIl5oXyI5znT+QqtWojASvRtqwRkVLnMeSaKt5mnzG8l4IbkVIP4I\r\n" +
                "/J2CoehEz+TkzHFgUinyqoW0I1oSeRtK4fwnlWdEXcizcmsKgtcnGp2pcb074n2f03eqD2a3ZkK9\r\n" +
                "xtVYk+GC+kCojFXCo9aCjYOZuHigkpxRP42oYjj6s+S3IPVipl6CxEeqaUeykQckMOp3wlwmm/+o\r\n" +
                "/Vew3GtVhsgw112+xinOwjflnTOr4JFEN+zoFdVlUhySOwkWcC+1kODIN7LGh6s4IklDtCN0WXU6\r\n" +
                "QjqioRX13EFytQpB9MP1a6ZRyVNTuEcEKZwxSj6tGJQDy9E5SJXD9z9IUivqjNGdGZ21Csoy+Zxz\r\n" +
                "vKdwDiPq1Bkn3qPFayuX2fUX6+/QwbWtrrd0UsrBYwCrTXoZeouyiiM52/RI90qj1TYjSXogCUWH\r\n" +
                "T/3b7I99tZfz1p17/e/bctkXVRe9Tp7au48n8fIe3YnTJzNSz1quuatdfN17pn67rJa/tdsxOS38\r\n" +
                "CrfHIHbdx+476UXKcBFRfTpJbMRdBb1HZwPy5M5KpFY44EEpiI567LvSXYV8qMWLgzJUi9GZ8v8E\r\n" +
                "z9ohuYlGV8RrzkUREbqohEVXvDpR9OdbONe1fgGC4Ai+IBszlwWnRmSpQhwU/CXKJayFjNgZif+E\r\n" +
                "KMfZdAU1M+6KeEc6XdDoATzRvl+Ioifo2ynXM+awPJBJli/0e4naKq622js53/BtOvU+r3Q/t7C7\r\n" +
                "6seP5dOKqCviyabjab4Es9s7Dy0uqhTFhPLObpWnaFDfmXQk0jO7ioh12FNpNJj3CdXnyNzf+dmc\r\n" +
                "0Cr3JFjL0J1+9l3ZG/X2FMTq7/3cxqNyFU8z/UofF+Wk2dE7BaPe/G2Ev0yKYDz97B88ZljoLKff\r\n" +
                "Z7lz9BWa59OK1hoKWk58Gc+TUv/S3qT6mNWvhS+x5ggP9jri2SVIlJSTesnYCP8X2jMRlSvty4qL\r\n" +
                "RncWS5MzuElbeDXbj4rMonqBUR8pxpnOPKo1yZi+IiySz09Oimfd9zvsJLZM4UuwxKhfywfsqwkS\r\n" +
                "jB3doztXqgP+E30YyjFrzEDlBahKpM/fgr0sbfEkiViFagnRw0TRv5Bci9rETP0I/vjUFKTqmDG3\r\n" +
                "dFJKyLFTjfQTZVzxfMHlOfjoyBkrK29Z8YdULYL3ku4RRBVRvRhnCX8y4hF01jNsbEXFu6ASy0By\r\n" +
                "STViNVHvT2aNUaU8dWcPmJc43WMCH/NzsJjneU1BIpHwa5Ha20rWeoep/Qv0ZHwXpcAiUXYIh+Ve\r\n" +
                "SB47OMmU/1f6e0H/GRo0OSTcwHaSAuOAF2lxBu6IDilmZHb555oiq30Zfki6U6xkgT0m0MAIJOOb\r\n" +
                "QF1GNZio4hI0m1V/CRYsM8ZNgr9IPcmWmClKJuIlwT9G6KV53QoNFGjto8OVkayq7X8P38gbUvga\r\n" +
                "flTfyOF77R8k26XuaqOM1x51bKunP2v/o/anqpOZ3gdF0EbTDv8Vqf9/7Z+cIDJ7+81p4yf7Whsu\r\n" +
                "e8bL/vu5fyGir0EQeCbpfqV4E0kqpUp6rVJs/Wdt7yQvs5drO1w/1d0cUSeKCi/6a3rm+DAisrF1\r\n" +
                "LKQpvpetjuPHSph0Coz9m54TWUdSH8ngcyL8W4ijjShtGeIgq8mU3VfCyILzGsovRM2zrvheqf1W\r\n" +
                "ubfWeD/IVv4T/lt5/3dd/RYkE/pm8uW/B9lT4/trpWQlTlay+EQ4vVU5D6Jlp792y0/9l1GeaVLf\r\n" +
                "67MA87fxB+1esG6rf6n2VuyS7053/6iWv+F/p1N45tx+hr9JL+8UxXdUGZtSuGH0wJ0bec47Vsw4\r\n" +
                "dYeMd6JoR07gmXeqxFgzbdU33PKjjrW4wmu5fmneuemZT6L7hXtXULxCAgvdv5I+E8WAhdYXykYN\r\n" +
                "MX2gfdHvjDsSolhGPclUFNo9I0I0G8mIXAltRuxLeDhbL4gkYv+czxfYxqKexmOCX1eqJRZYPues\r\n" +
                "J40xtm9VCP9xfZaAVTLpPcETJlC4ahTnWP0GChmVJKJvQv8zxOzxYgmCvHjEMMq13c9NN2PnmGaR\r\n" +
                "TM5YL9FtcqMsMfmyZGR2wIqR8hZLOQFHSJP+6sb6thDX/6O9ypYkx20gX6Yl3jqru8fr8DW74Qg/\r\n" +
                "+/9/zWIiAVLVNet9sINRKokEQZAEEgmRrTz13UmuW8hQG258XKPv8JcMH2yR8EA74aP+Gm1x4of4\r\n" +
                "rHiuiOLzGtmAsR/AiIaLLVIO3ORkfP11tgjmAa9aevoPsGWHf7dYioiTA9j867XmD0bWDtSvfFto\r\n" +
                "bTaGt/JZbgiyII4b0jfk/GTfDgxcEaGCtivOqCC2VUtF9Kim7/DTihhteUck3hnXiiMF/dt1bhLN\r\n" +
                "87WLBbf2C+1ecR8VnlLQozhRMdbOfIeO4P6N/wN2nQMOJ0g2FP5twNWNpyIywisq71WQqxD/NthZ\r\n" +
                "udsNUgX9lbPWS/P8u/cYiTNjU9b13xlHr9lOrBqJ3AczSWUuanfeLP8relee8sLbK7ypwh00ZKlA\r\n" +
                "kk/cklRi9QknR2uFQ2r2FiR8lptNOiL6fkUUbPhegJDydp+1oD8bIgsaB+SaQP6wEMUX8g3BxALf\r\n" +
                "CMbQRfNq5x3INwRNV74JG12ZLxJ8t729E/kXZhRlQpoRBJFXaEqcr60OzwSGUvC1sWeD3hX48DPO\r\n" +
                "FfgdB10BPZvlpX2wq799gCktRDCPuI2scb4BDeUUJGsmoN1z8/Y24y4EEzf+Zo7JqtlsE44mVszY\r\n" +
                "o7+dWXDpRfM8F48seNh592rXU0+kFuVpkRGTYK/WYaOERJZWoRLfkTlTIknGphsDDNAUwI/77EA/\r\n" +
                "Vw3yrRxSmW0wvV2bzFo42qKtRcQb7jECSwIipN1otTq2UOvskkVbRX/CM/N0KniB3O03onY7qx33\r\n" +
                "XqwKlWhe8b7inD12qOc4ZtV/IYM1vJbsccKy4/pvddiBKuxwf7/0nK7Vqjtib0eefKf8CZQ6kJNO\r\n" +
                "rHiSBW/IqyuwQPnpTlTdKaW8WUckb32Q9e9E+GPIWTu1HXwGntAYUW2PnwMrmlFfzKxSn79Uqg7S\r\n" +
                "iTxqHN/ALDqvCuAidx72xvfJZklElifOtr3gcWWwbKbm5r9tz694X193MiuVfUzwQ7Uu3eQDf+la\r\n" +
                "8c0JQ5zBU6bhxPzN5jBoG09KLZtsfMXKykp7W1zn0J6V4s945hsjJDJqV/Imj9vOPGVBhUxUmRmn\r\n" +
                "Kzx1ZPue6BwQkQG3lJxnHVldZ7AzV3jOwpNTPqf14mx4Il6kuV1O8DR7K1YpiGbJZRMidcLpKtZI\r\n" +
                "5SJ5bSceeebHiTYkQ5nuWcIUq1UIC9bzzJzCBRZjUcJzlj/QIjR5+06mI5JFLcNYpW6RGb8W7EC/\r\n" +
                "55v+BZYGk+salcVIm51w9z4vPGnJ3F3CKW60NuK72t7fnFRY48zytO/ZjdboOol8pVsZTSqBkyqn\r\n" +
                "EIYScdsRfjSxLxNRPO/VE+3VoxvWPJiRxbsy2VGlBQnvAYzGEy0kd1b+xBtmcrym5xO2nIySCT6j\r\n" +
                "KKlZf7bckOnT0XysQmrCWVTMzU6ym1p5Wv5NjIxsmVO8NV8Y7Rk5zYpvQyauyCk/oF9QfYXF6vvK\r\n" +
                "ZxqX+T7k495m6tGn5Loxz2+4V28SE+oxmbda3bnj/81q0fBytb6icoexf/pdeYnY+kJPwIl/nelp\r\n" +
                "qaDWBCZRmfMKvFu8bANaCsPfWM9k1rULcm2w+mthfVXA4DJ5g/jnylOvrAYy2VbGd5M+0au1aYYP\r\n" +
                "B3JDwb5KX/CoWSPOP7tg3j8Zb5SMMRFDkxN+IUwl4ZwOzG/+f+CGCj2nadrhSRINE5nVcvU0nRv9\r\n" +
                "bWGktGwyQXYhtioSRJ5BdH+C762sYNcrFv9s+CB9D3vLnFNf3tr/v9397Jm7ypcfxpSvjn432bd3\r\n" +
                "nQ/29su13x2/H2B07ZxP3M9kPKgij8fhFkVTpTdKNqrwRfHISqxq+fA7KtrHpfUBvQ+wyP3CrPZ8\r\n" +
                "B/880Nv6uqSwyANtg+yBp+iQ0R3MUez/hd8b5APk9y/t4AxtJ+Oq69sHva80bF8kP25fnScl8JfJ\r\n" +
                "EFui6c5WBXufmZ6y1/T0Prbpy6yftWmQrry7mfE14V5nYzTKoTpL6/aKTP+ajU/1Vvg/Dc/O/+bb\r\n" +
                "l/ZpBStYrByn43I0r4/gEn7wfs/sujBXSZ5NNhbIHuU/uldMVNaIput59Ct/De5ZewAm3uM3Um+T\r\n" +
                "ebDW2q79PVAtHfgveH/Ag1cgd2Ul5DneIqrFiETWA63VbgVyCz01ozcz8iQrZOK+IH/PHJlcQ7li\r\n" +
                "Jvp1ljRyvfSFzb36zjY7G1NStjbyqPs87alkaQFWBtuJIoziibSZ/976RmmZMQO3x/kFHlK/9Ffr\r\n" +
                "G7VEnqB+J56QMCORCMxb/ZYr2M2I12Xw5Whe/MwpNtyLvKfB95Pr/CEOLZEpRltZuaistwxyXTbz\r\n" +
                "19tye5daRLEqAXmi2f7cktmh2vMLiXENz19i32xS4bab5AI1d42B/XJ28XaGusuDNeLY2qx/ArNb\r\n" +
                "7CT3D0ZcufLdb/DaFXiekQt2jL7ardpw/xZLKuJLRg/4kdiVKeWtJ2Bc8UsqOKlhk1Nu3dGt+0fl\r\n" +
                "3jsm5kHnyBG6RIJlf0OUJFRLWrfo2UhW8Twl4Q465lFHeMrF4TSV06VBVrGw31Cy99FfE/cZyPwS\r\n" +
                "OWW6zr5bpX3qE8nY5DueEp0qXxhBUg147sNzrwFetEO+8ZsK5N1w8xWoueEGRm8rxosj7ub5vvVe\r\n" +
                "y+CzkRw62pqz02is4BgreOnKtvB3ACWF5ax4q8gABexLUV7GFnwf8PPK/wM+t8GLD2SC9p8wb4P0\r\n" +
                "hi9ZLWOWsLXEc8imdSc2F1QeBzNSZtUhcXtYrtho2QGMX2HJjpUOWPAAl1TetvItQ74yyy34j1hR\r\n" +
                "+Z1UcoHc6+AqFTIFczLl5SSD7f2AfQl73cHx9WR1j8nOWO2pZl3CW8Spe+xFz2Q1PQdtXoybblhL\r\n" +
                "ziuZjPh1MJ+dmXskVjTWpsumT5yUxuxJZGzvuyHHMmQEQYWVeSGS6Y586Z5ZDiKN5pB3p9lCZrS9\r\n" +
                "vmP8ucZJnJcGTNFW8Iuw2DvN2gH5PDCTyLrLpbcMWKY1hOY7HcluzJKBMZS4VvPgifiSiWFdLg1z\r\n" +
                "e69yPR3reUaePW93vLzPT7fvjDvdcfcJu9c9ztiNZH7hhqK9EF+F4xb0eezHX5XBiNreVgnMftEp\r\n" +
                "k1DmsfAtGXYp75I6S5q8Z7REBliMPUlvJYpmk1PJbDMUmbUn3XTqVxzmtchPTnO3SGxcZ8d3HCyK\r\n" +
                "pi+60dLEPVSeisROoJclInsk1z/s+WAUtbWi+X3gut0zlF10j+g1gGbPdGXNZ/50YCcPfO3mv03m\r\n" +
                "YfsVrpNwK+ozxc5XudLX+uN1m/+w5DK8r07rmGjPsc7RM9As3ZlHsthQbrfYfY58T//DcD6ah/Vd\r\n" +
                "IlZ9JLmOIr1qmi3+FcVH/uJ578qZR+4ViF9+6IvwPvVN9a57096NtiXzP22L+WI0+YisoWw/uc4D\r\n" +
                "7lxZbFipp8dLsvcxcsaeHmujbLJ40d7AyGl7fyDrRMamVg2Cyy2Dn2BEcoKCQhm7iMQ+75QP9nql\r\n" +
                "I2tn9iOT72yumhcsdh9qZfeS6O4ek8n5Kn3Ev/DNYH478mbF2i7nXc90kfESEZUbNQT7CqwpJS48\r\n" +
                "PUdWnZ2y1GrM3A8rKs9UKe80d6iHzrBkcso5/eA3H1d+/1+3v6B9XO1x3fLj8oIH2wcqmM/r7Z09\r\n" +
                "jb+I1GlSr9ph46rxRN9JPrMjb0lsbsjEFVVAO4/JMKYYasldnOBG36/W6o/363+9NC9kkcK716u3\r\n" +
                "Yet3nP8KiRX1WMVogYYVGUFkm699IKMuzDABXC9jzhhXdYgwxaYy4FVBvPQYlMxZKaWRnYllmqnE\r\n" +
                "n8sQr8WqBPX+e24T9hwttjKt6FVFBYuYXecZkRWbMO1IBjcTOaXinIB5Ebs/zDtn5MZIv6xOcqXW\r\n" +
                "VhXy88U/2nODzGYRoV4/W5z0OPa0r41N8HflctMQvff2zCl7vN3bCr0T7J1wEoUrVMRWwKj0CG+e\r\n" +
                "2MTb5kuDJ6IUyyLxFqcSn3XIrpE8fKbHyu46Ikk9sHCFGX75H/brpTeqKg4A+LltKW2ZB69GU4KZ\r\n" +
                "6KaGh4AhyrKUAoZEDbAANNEGhnagtGaKRHfG6MbEjSbGxJ07NobohqXxExjjB/AjuHDjSs899955\r\n" +
                "EFEiPiDzm8lv5n/u3HPmPv73PKoeYHjs+6P3tlCtVft5NN3LmSofar2rXK1A+yNo1UMP9j/Fe9dA\r\n" +
                "H1X1nvXy92bKxmq+dW8f3h+PGsm2MreLIyq25euhPaGYezfT81qMbMXqqdZbw+5IY0o/bqaVWDOt\r\n" +
                "mJrl1mY5tlelmfQUz5V7NtJ8enbomvf71epuTvdWRdWd25pyZaaXg7WkuGpF7f5zXuutOGrpDlRn\r\n" +
                "UeR0v7cvruITZWbUQjGHLI6hn8nV89GfN9bSE1QLg7PM4TXTdBjOiupuFHtWvWXRn1Z50SiPr56O\r\n" +
                "rVlmQD1eq0Y55hZjzFQa54szm0t1dvdany7HwFqsNZNqzZb9RZFrRdZMp/swne7U7vTkVbORYhbX\r\n" +
                "TP+yvZzbzqQ9i2ObTde/1nuuqitQrPiq2e5s2WYjPQ170qqoyNN8dC4yZ3CeU9yVevlfw/Or+kA+\r\n" +
                "D2d3dX/y2pPpek0NPaWNMLj669dvlLPBmZT5s+XZb0srltlQrQD7T2sznf9sOZcqMr3WO7v+vLKa\r\n" +
                "KfXn1TtTe4Pveu+cdg08l1WdvI3ZMvdrZY7mZ1e8ngYAAIARdgKAh/YzMGqy7wAAAHgcjb0Fj4Bf\r\n" +
                "/h3jHwEAAPz3Jj54OFteBAAGTU4BD2rrPABEP/Kgpn54NEz/BAAAAACPhplvAYB7bZsHAABGQW0M\r\n" +
                "Sp8BAAAAAAAA/P/q7wAAAAAAAAAAjJ7GBAAAAAAAwOhpXgEAAAAAAAAAAAAAAAAAAAAYtn1shHwP\r\n" +
                "AAAAPE52fAUAjKKdnwMAAAAAAMDo2v0UAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAj\r\n" +
                "Kgth45Ps9XA45K9YCs+FZ2OwHL4Oxasevkzb89dCuU8I43H7QhlPxPjlMp6M8WtlvDU8H1bjntnE\r\n" +
                "dCy9H74o4yzMZXvLeCw0shfKeDxuXyrjiRi/UcaTMX63jLeGq9mneTw1EeML2a+3W8fbnWud9ZXW\r\n" +
                "pdWN9ZWLndbSWvvyze7GeufyZmtx4+D+1uFjx44eOHLo0NH9rYW1tVa3s7J6c7PVbW+2u7faV5bO\r\n" +
                "LC2cP7XvXOfG6Xbn/oXz3eUr7RvL3eutjat//ZeLq5315XA7tMLx0A6dcC1aDyuxfCleko0UX4zb\r\n" +
                "WmEprMU9LoeboZu2d2K8GbcvxtLBsD9Gh8Ox+D4aDoQj4VB8H01bF2K9tfjdjTVWYps3U61ubGsz\r\n" +
                "6oZb8fNKbP1MtBDOh1NhXzgX970RTqcjOhs/V8LbsY3luPf99/s7v5yPLS7Hf2/HLXnr1+ORbYSr\r\n" +
                "/8jVWIw18trLf/Ivl+JxvBJT8lRs9aX7tDrY5tBRLcxlO7Lt8VrPZ9uzRpiP383yu5bVw8n4XS/L\r\n" +
                "jVh+stj+zcn5V7O72W8ffhz23jly5uyFO+/tvXg3m1xajR/PnLibTeTRRB5tSdHSmzHKi2O94lhe\r\n" +
                "HM+LY3lxPC+G3q8hL2Z5MeTF7JkT2b7i9bsAAwBV8KVcCmVuZHN0cmVhbQplbmRvYmoKMzcgMCBv\r\n" +
                "YmoKPDwvVHlwZS9Gb250RGVzY3JpcHRvci9DSURTZXQgMzggMCBSL0Rlc2NlbnQgLTE1Ni9TdGVt\r\n" +
                "ViA3Ni9Gb250V2VpZ2h0IDQwMC9DYXBIZWlnaHQgNjg4L0ZvbnRCQm94Wy0xMiAtMTU2IDk5NiA4\r\n" +
                "NTldL0ZvbnRGaWxlMiAzOSAwIFIvRm9udFN0cmV0Y2gvTm9ybWFsL1hIZWlnaHQgNDU3L0ZsYWdz\r\n" +
                "IDQvRm9udEZhbWlseSj+/57RT1MpL0FzY2VudCA4NTkvRm9udE5hbWUvRUtFQVRHK1NpbUhlaS9J\r\n" +
                "dGFsaWNBbmdsZSAwPj4KZW5kb2JqCjM1IDAgb2JqCjw8L0Jhc2VGb250L0VLRUFURytTaW1IZWkv\r\n" +
                "Q0lEU3lzdGVtSW5mbyAzNiAwIFIvVHlwZS9Gb250L1N1YnR5cGUvQ0lERm9udFR5cGUyL0ZvbnRE\r\n" +
                "ZXNjcmlwdG9yIDM3IDAgUi9EVyAxMDAwL0NJRFRvR0lETWFwL0lkZW50aXR5Pj4KZW5kb2JqCjM0\r\n" +
                "IDAgb2JqClszNSAwIFJdCmVuZG9iago0MCAwIG9iago8PC9MZW5ndGggNDEzL0ZpbHRlci9GbGF0\r\n" +
                "ZURlY29kZT4+c3RyZWFtCkiJXJLPjqMwDMbvPEWOM4cRCSEhSBUSEJB62D/a7j4ABbeDNA0opYe+\r\n" +
                "/dpmNCttJJB++mzH/py0PfpjmDeR/ozLeIJNXOYwRbgvjziCOMN1DonKxDSP2yfxf7wNa5Ji8ul5\r\n" +
                "3+B2DJclORxE+gvF+xaf4qWeljO8JumPOEGcw1W8/GlPryI9Pdb1A24QNiFFVYkJLljo27B+H24g\r\n" +
                "Uk57O06oz9vzDXP+Rfx+riAyZrU3My4T3NdhhDiEKyQHiacShx5PlUCY/tMzs6edL+P7EDHcFxbD\r\n" +
                "TZHLCqnXnqiUBVKeKY3kGu+RVNP1SNY0JWmdJ610JWmZVh1pbedIs5Jqut5TTaUc12wM1dSqyZGK\r\n" +
                "pujoPqNKpLzrNOUp7/i+OiPNqoK0vlOkOUlUKq+4JkeaPVKpvmZqc8rTrWUDVE2arQ11Jm1P5Cz1\r\n" +
                "YjNNvcg64850v1NOE5lc0kSydA1rxtB8jSUqENkJ27IT2lKkt7uDmqeVsmQH0R6y/9NnWgS+F/G1\r\n" +
                "5fERIy6YHxVvlnY6B/h6d+uyCsyiL/krwABHG7U1CmVuZHN0cmVhbQplbmRvYmoKMzMgMCBvYmoK\r\n" +
                "PDwvRGVzY2VuZGFudEZvbnRzIDM0IDAgUi9CYXNlRm9udC9FS0VBVEcrU2ltSGVpL1R5cGUvRm9u\r\n" +
                "dC9TdWJ0eXBlL1R5cGUwL0VuY29kaW5nL0lkZW50aXR5LUgvVG9Vbmljb2RlIDQwIDAgUj4+CmVu\r\n" +
                "ZG9iagoyIDAgb2JqCjw8L0Nyb3BCb3hbMC4wIDAuMCAzMTkuNDY1IDM1MC42NF0vUGFyZW50IDQx\r\n" +
                "IDAgUi9UeXBlL1BhZ2UvQ29udGVudHNbMyAwIFIgNCAwIFIgNSAwIFJdL1Jlc291cmNlczw8L1hP\r\n" +
                "YmplY3Q8PC9YaTE3IDYgMCBSL1hpMTggMTIgMCBSL1hpMTUgMTMgMCBSL1hpMTYgMTQgMCBSL1hp\r\n" +
                "MTMgMTUgMCBSL1hpOCAxNiAwIFIvWGkxNCAxNyAwIFIvWGk5IDE4IDAgUi9YaTExIDE5IDAgUi9Y\r\n" +
                "aTEyIDIwIDAgUi9YaTQgMjEgMCBSL1hpNSAyMiAwIFIvWGk2IDIzIDAgUi9YaTcgMjQgMCBSL1hp\r\n" +
                "MCAyNSAwIFIvWGkxIDI3IDAgUi9YaTE5IDI5IDAgUi9YaTIgMjggMCBSL1hpMyAzMCAwIFIvWGky\r\n" +
                "MCAzMSAwIFIvWGkxMCAzMiAwIFI+Pi9Qcm9jU2V0Wy9QREYvVGV4dC9JbWFnZUIvSW1hZ2VDL0lt\r\n" +
                "YWdlSV0vRm9udDw8L0MyXzAgMzMgMCBSPj4+Pi9NZWRpYUJveFswLjAgMC4wIDMxOS40NjUgMzUw\r\n" +
                "LjY0XS9TdHJ1Y3RQYXJlbnRzIDE3L1JvdGF0ZSAwPj4KZW5kb2JqCjQxIDAgb2JqCjw8L0lUWFQo\r\n" +
                "NS4zLjEpL1R5cGUvUGFnZXMvQ291bnQgMS9LaWRzWzIgMCBSXT4+CmVuZG9iago0MiAwIG9iago8\r\n" +
                "PC9UeXBlL0NhdGFsb2cvQWNyb0Zvcm0gMSAwIFIvUGFnZXMgNDEgMCBSPj4KZW5kb2JqCjQzIDAg\r\n" +
                "b2JqCjw8L1Byb2R1Y2VyKGlUZXh0riA1LjMuMSCpMjAwMC0yMDEyIDFUM1hUIEJWQkEgXChBR1BM\r\n" +
                "LXZlcnNpb25cKSkvTW9kRGF0ZShEOjIwMTcwOTI1MTY1NDM4KzA4JzAwJykvQ3JlYXRpb25EYXRl\r\n" +
                "KEQ6MjAxNzA5MjUxNjU0MzgrMDgnMDAnKT4+CmVuZG9iagp4cmVmCjAgNDQKMDAwMDAwMDAwMCA2\r\n" +
                "NTUzNSBmIAowMDAwMDAwMDAwIDY1NTM1IGYgCjAwMDAwMzA2NzEgMDAwMDAgbiAKMDAwMDAwMDAx\r\n" +
                "NSAwMDAwMCBuIAowMDAwMDAwMDkxIDAwMDAwIG4gCjAwMDAwMDA1MzIgMDAwMDAgbiAKMDAwMDAw\r\n" +
                "MTM5OSAwMDAwMCBuIAowMDAwMDAxMjk0IDAwMDAwIG4gCjAwMDAwMDEyNzEgMDAwMDAgbiAKMDAw\r\n" +
                "MDAwMTEyMSAwMDAwMCBuIAowMDAwMDAwODM4IDAwMDAwIG4gCjAwMDAwMDA5MDIgMDAwMDAgbiAK\r\n" +
                "MDAwMDAwMTcxNCAwMDAwMCBuIAowMDAwMDAyMDM2IDAwMDAwIG4gCjAwMDAwMDIzNjcgMDAwMDAg\r\n" +
                "biAKMDAwMDAwMjY4MCAwMDAwMCBuIAowMDAwMDAyOTkyIDAwMDAwIG4gCjAwMDAwMDMzMTkgMDAw\r\n" +
                "MDAgbiAKMDAwMDAwMzYzNSAwMDAwMCBuIAowMDAwMDAzOTU3IDAwMDAwIG4gCjAwMDAwMDQyODMg\r\n" +
                "MDAwMDAgbiAKMDAwMDAwNDU5OSAwMDAwMCBuIAowMDAwMDA0OTM4IDAwMDAwIG4gCjAwMDAwMDUy\r\n" +
                "NjYgMDAwMDAgbiAKMDAwMDAwNTU5MiAwMDAwMCBuIAowMDAwMDA1OTk5IDAwMDAwIG4gCjAwMDAw\r\n" +
                "MDU5MTAgMDAwMDAgbiAKMDAwMDAwNjUwMiAwMDAwMCBuIAowMDAwMDA2OTUzIDAwMDAwIG4gCjAw\r\n" +
                "MDAwMDcyODAgMDAwMDAgbiAKMDAwMDAwNzYxOCAwMDAwMCBuIAowMDAwMDA3ODM4IDAwMDAwIG4g\r\n" +
                "CjAwMDAwMDgwNTkgMDAwMDAgbiAKMDAwMDAzMDU0MyAwMDAwMCBuIAowMDAwMDMwMDM3IDAwMDAw\r\n" +
                "IG4gCjAwMDAwMjk4OTAgMDAwMDAgbiAKMDAwMDAwODM5MyAwMDAwMCBuIAowMDAwMDI5NjM0IDAw\r\n" +
                "MDAwIG4gCjAwMDAwMDg0NjIgMDAwMDAgbiAKMDAwMDAwODYzMSAwMDAwMCBuIAowMDAwMDMwMDYy\r\n" +
                "IDAwMDAwIG4gCjAwMDAwMzExNjIgMDAwMDAgbiAKMDAwMDAzMTIyNiAwMDAwMCBuIAowMDAwMDMx\r\n" +
                "Mjg4IDAwMDAwIG4gCnRyYWlsZXIKPDwvUm9vdCA0MiAwIFIvSUQgWzw3OWE3ZThhZWJjMjAzOGUw\r\n" +
                "MTM3ZWM2ODQyYTJhMTc1OD48YmVmNTE1Y2MyMzVhNTI0NTI0MGE2OGViZDAxODBhMzM+XS9JbmZv\r\n" +
                "IDQzIDAgUi9TaXplIDQ0Pj4Kc3RhcnR4cmVmCjMxNDQyCiUlRU9GCg==\r\n" +
                "";
        //str = new String(str.getBytes("ISO-8859-1"), "utf-8");
        base64Contents.add(str);
        System.out.println(EncodingUtil.getEncoding(str));
        System.out.println(str);
        InputStream mergePdf = mergeBase64ToPdf(base64Contents);
        fileUtil.InputToFile(mergePdf, "E:/mergetest11.pdf");
    }

    /**
     * @param ArrayList<string> base64 string集合
     * @return pdf文件
     * @throws IOException
     * @description 将多个base64文件解码并合成一个pdf文件
     */
    public static InputStream mergeBase64ToPdf(List<String> base64Contents) throws IOException {
        List<InputStream> sources = new ArrayList<InputStream>();
        InputStream inputStream = null;
        InputStream mergePdf = null;
        try {
            for (int i = 0; i < base64Contents.size(); i++) {
                inputStream = fileUtil.getBase64FileToStream(base64Contents.get(i));
                sources.add(inputStream);
            }
            MergePdf app = new MergePdf();
            mergePdf = app.merge(sources);
            return mergePdf;
        } finally {
            inputStream.close();
        }
    }

    /**
     * @param ArrayList<string> pdf pathname 集合
     * @throws IOException
     * @description 将多个pdf文件合并成一个
     */
    public static void mergeMultiPDfToOne() throws IOException {
        FileOutputStream newPdf = new FileOutputStream("E:/merge2.pdf");
        InputStream inputStream1 = null;
        InputStream inputStream2 = null;
        int ch = 0;
        MergePdf app = new MergePdf();
        InputStream mergePdf = null;
        try {
            inputStream1 = new FileInputStream("E:/test.pdf");
            inputStream2 = new FileInputStream("E:/test2.pdf");
            List<InputStream> sources = new ArrayList<InputStream>();
            sources.add(inputStream1);
            sources.add(inputStream2);
            mergePdf = app.merge(sources);
            while ((ch = mergePdf.read()) != -1) {
                newPdf.write(ch);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            newPdf.flush();
            inputStream1.close();
            inputStream2.close();
            newPdf.close();
            mergePdf.close();
        }
    }

    /**
     * Creates a compound PDF document from a list of input documents.
     * <p>
     * The merged document is PDF/A-1b compliant, provided the source documents are
     * as well. It contains document properties title, creator and subject,
     * currently hard-coded.
     *
     * @param sources list of source PDF document streams.
     * @return compound PDF document as a readable input stream.
     * @throws IOException if anything goes wrong during PDF merge.
     */
    public InputStream merge(final List<InputStream> sources) throws IOException {
        String title = "My title";
        String creator = "Alexander Kriegisch";
        String subject = "Subject with umlauts 1";

        ByteArrayOutputStream mergedPDFOutputStream = null;
        COSStream cosStream = null;
        try {
            // If you're merging in a servlet, you can modify this example to use the
            // outputStream only
            // as the response as shown here: http://stackoverflow.com/a/36894346/535646
            mergedPDFOutputStream = new ByteArrayOutputStream();
            cosStream = new COSStream();

            PDFMergerUtility pdfMerger = createPDFMergerUtility(sources, mergedPDFOutputStream);

            // PDF and XMP properties must be identical, otherwise document is not PDF/A
            // compliant
            PDDocumentInformation pdfDocumentInfo = createPDFDocumentInfo(title, creator, subject);
            PDMetadata xmpMetadata = createXMPMetadata(cosStream, title, creator, subject);
            pdfMerger.setDestinationDocumentInformation(pdfDocumentInfo);
            pdfMerger.setDestinationMetadata(xmpMetadata);

            LOG.info("Merging " + sources.size() + " source documents into one PDF");
            pdfMerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
            LOG.info("PDF merge successful, size = {" + mergedPDFOutputStream.size() + "} bytes");

            return new ByteArrayInputStream(mergedPDFOutputStream.toByteArray());
        } catch (BadFieldValueException | TransformerException e) {
            throw new IOException("PDF merge problem", e);
        } finally {
            for (InputStream source : sources) {
                IOUtils.closeQuietly(source);
            }
            IOUtils.closeQuietly(cosStream);
            IOUtils.closeQuietly(mergedPDFOutputStream);
        }
    }

    private PDFMergerUtility createPDFMergerUtility(List<InputStream> sources,
                                                    ByteArrayOutputStream mergedPDFOutputStream) {
        LOG.info("Initialising PDF merge utility");
        PDFMergerUtility pdfMerger = new PDFMergerUtility();
        pdfMerger.addSources(sources);
        pdfMerger.setDestinationStream(mergedPDFOutputStream);
        return pdfMerger;
    }

    private PDDocumentInformation createPDFDocumentInfo(String title, String creator, String subject) {
        LOG.info("Setting document info (title, author, subject) for merged PDF");
        PDDocumentInformation documentInformation = new PDDocumentInformation();
        documentInformation.setTitle(title);
        documentInformation.setCreator(creator);
        documentInformation.setSubject(subject);
        return documentInformation;
    }

    private PDMetadata createXMPMetadata(COSStream cosStream, String title, String creator, String subject)
            throws BadFieldValueException, TransformerException, IOException {
        LOG.info("Setting XMP metadata (title, author, subject) for merged PDF");
        XMPMetadata xmpMetadata = XMPMetadata.createXMPMetadata();

        // PDF/A-1b properties
        PDFAIdentificationSchema pdfaSchema = xmpMetadata.createAndAddPFAIdentificationSchema();
        pdfaSchema.setPart(1);
        pdfaSchema.setConformance("B");

        // Dublin Core properties
        DublinCoreSchema dublinCoreSchema = xmpMetadata.createAndAddDublinCoreSchema();
        dublinCoreSchema.setTitle(title);
        dublinCoreSchema.addCreator(creator);
        dublinCoreSchema.setDescription(subject);

        // XMP Basic properties
        XMPBasicSchema basicSchema = xmpMetadata.createAndAddXMPBasicSchema();
        Calendar creationDate = Calendar.getInstance();
        basicSchema.setCreateDate(creationDate);
        basicSchema.setModifyDate(creationDate);
        basicSchema.setMetadataDate(creationDate);
        basicSchema.setCreatorTool(creator);

        // Create and return XMP data structure in XML format
        try (ByteArrayOutputStream xmpOutputStream = new ByteArrayOutputStream();
             OutputStream cosXMPStream = cosStream.createOutputStream()) {
            new XmpSerializer().serialize(xmpMetadata, xmpOutputStream, true);
            cosXMPStream.write(xmpOutputStream.toByteArray());
            return new PDMetadata(cosStream);
        }
    }

    static BASE64Decoder decoder = new BASE64Decoder();

    public static String base64StringToPDF(String base64sString) {
        Long start = System.currentTimeMillis();
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        String path = "";
        try {
            //将base64编码的字符串解码成字节数组
            byte[] bytes = decoder.decodeBuffer(base64sString);
            //apache公司的API
            //byte[] bytes = Base64.decodeBase64(base64sString);
            //创建一个将bytes作为其缓冲区的ByteArrayInputStream对象
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            //创建从底层输入流中读取数据的缓冲输入流对象
            bin = new BufferedInputStream(bais);
            //指定输出的文件http://m.nvzi91.cn/nxby/29355.html
            path = WXPayUtil.getCurrentTimestampMs() + ".pdf";
            String basePath = "C:/apache-tomcat-8.5.13/ROOT/mzkd/WEB-INF/swagger/pdf/";
            File baseFile = new File(basePath);
            if (!baseFile.exists()) {
                baseFile.mkdirs();
            }
            File file = new File(basePath + path);
            //创建到指定文件的输出流
            fout = new FileOutputStream(file);
            //为文件输出流对接缓冲输出流对象
            bout = new BufferedOutputStream(fout);
            byte[] buffers = new byte[1024];
            int len = bin.read(buffers);
            while (len != -1) {
                bout.write(buffers, 0, len);
                len = bin.read(buffers);
            }
            //刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
            bout.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bin.close();
                fout.close();
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Long end = System.currentTimeMillis();
        System.out.println(end - start);
        return path;
    }
}