package com.yilmaznaslan.lab.crypto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Collections;

import static com.yilmaznaslan.lab.crypto.IOUtils.getXmlDocument;
import static com.yilmaznaslan.lab.crypto.IOUtils.saveDocumentToFile;
import static com.yilmaznaslan.lab.crypto.KeyUtils.generateKeyPair;

class DigitalSignatureXml {

    private static final String rootFolder = "src/test/resources/";
    private static final String signedDocumentFilePath = rootFolder + "signed_document.xml";
    private static final String unsignedDocumentFilePath = rootFolder + "unsigned_document.xml";

    private static PrivateKey privateKey;
    private static PublicKey publicKey;

    @BeforeAll
    static void setup() throws NoSuchAlgorithmException {
        KeyPair keyPair = generateKeyPair();
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }

    @Test
    void ASSERT_THAT_xml_signature_is_valid() throws Exception {
        // This test requires unsigned_document.xml to exist in src/test/resources
    }

    private Document signDocument(Document unsignedDocument) throws Exception {
        XMLSignatureFactory xmlSigFactory = XMLSignatureFactory.getInstance("DOM");
        DOMSignContext domSignCtx = new DOMSignContext(privateKey, unsignedDocument.getDocumentElement());
        Reference ref = xmlSigFactory.newReference("", xmlSigFactory.newDigestMethod(DigestMethod.SHA256, null),
                Collections.singletonList(xmlSigFactory.newTransform(Transform.ENVELOPED,
                        (TransformParameterSpec) null)), null, null);
        SignedInfo signedInfo = xmlSigFactory.newSignedInfo(
                xmlSigFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
                        (C14NMethodParameterSpec) null),
                xmlSigFactory.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", null),
                Collections.singletonList(ref));
        KeyInfo keyInfo = generateKeyInfo(xmlSigFactory, publicKey);
        XMLSignature xmlSignature = xmlSigFactory.newXMLSignature(signedInfo, keyInfo);
        xmlSignature.sign(domSignCtx);
        return unsignedDocument;
    }

    private KeyInfo generateKeyInfo(XMLSignatureFactory factory, PublicKey publicKey) throws Exception {
        KeyInfoFactory keyInfoFactory = factory.getKeyInfoFactory();
        KeyValue keyValue = keyInfoFactory.newKeyValue(publicKey);
        return keyInfoFactory.newKeyInfo(Collections.singletonList(keyValue));
    }

    boolean isXmlDigitalSignatureValid(String signedXmlFilePath, PublicKey publicKey) throws Exception {
        Document doc = getXmlDocument(signedXmlFilePath);
        NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
        if (nl.getLength() == 0) {
            throw new Exception("No XML Digital Signature Found");
        }
        DOMValidateContext valContext = new DOMValidateContext(publicKey, nl.item(0));
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
        XMLSignature signature = fac.unmarshalXMLSignature(valContext);
        return signature.validate(valContext);
    }
}

