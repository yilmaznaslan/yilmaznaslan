package com.yilmaznaslan.tutorial.cryptography;

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

import static com.yilmaznaslan.tutorial.cryptography.IOUtils.getXmlDocument;
import static com.yilmaznaslan.tutorial.cryptography.IOUtils.saveDocumentToFile;
import static com.yilmaznaslan.tutorial.cryptography.KeyUtils.generateKeyPair;

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
        // GIVEN
        Document documentUnsigned = getXmlDocument(unsignedDocumentFilePath);

        // WHEN
        Document document = signDocument(documentUnsigned);
        saveDocumentToFile(document, signedDocumentFilePath);

        // THEN
        Assertions.assertTrue(isXmlDigitalSignatureValid(signedDocumentFilePath, publicKey));
    }

    @Test
    void ASSERT_THAT_xml_signature_is_not_valid_WHEN_public_key_is_wrong() throws Exception {
        // GIVEN
        Document documenUnsigned = getXmlDocument(unsignedDocumentFilePath);
        PublicKey wrongPublicKey = generateKeyPair().getPublic();

        // WHEN
        Document document = signDocument(documenUnsigned);
        saveDocumentToFile(document, signedDocumentFilePath);

        // THEN
        Assertions.assertFalse(isXmlDigitalSignatureValid(signedDocumentFilePath, wrongPublicKey));
    }

    @Test
    void ASSERT_THAT_xml_signature_is_not_valid_WHEN_data_is_modified() throws Exception {
        // GIVEN
        Document documentUnsigned = getXmlDocument(unsignedDocumentFilePath);

        // WHEN
        Document document = signDocument(documentUnsigned);
        document.getElementsByTagName("Organisation").item(0).setTextContent("Hello");
        saveDocumentToFile(document, signedDocumentFilePath);

        // THEN
        Assertions.assertFalse(isXmlDigitalSignatureValid(signedDocumentFilePath, publicKey));
    }

    private Document signDocument(Document unsignedDocument) throws Exception {

        //Create XML Signature Factory
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

        //Pass the Public Key File Path
        KeyInfo keyInfo = generateKeyInfo(xmlSigFactory, publicKey);

        //Create a new XML Signature
        XMLSignature xmlSignature = xmlSigFactory.newXMLSignature(signedInfo, keyInfo);

        //Sign the document
        xmlSignature.sign(domSignCtx);

        return unsignedDocument;
    }

    private KeyInfo generateKeyInfo(XMLSignatureFactory factory, PublicKey publicKey) throws Exception {
        // Create KeyInfoFactory instance
        KeyInfoFactory keyInfoFactory = factory.getKeyInfoFactory();

        // Create a KeyValue containing the PublicKey
        KeyValue keyValue = keyInfoFactory.newKeyValue(publicKey);

        // Create and return a KeyInfo containing the KeyValue
        KeyInfo keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(keyValue));
        return keyInfo;
    }

    boolean isXmlDigitalSignatureValid(String signedXmlFilePath, PublicKey publicKey) throws Exception {
        Document doc = getXmlDocument(signedXmlFilePath);
        NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
        if (nl.getLength() == 0) {
            throw new Exception("No XML Digital Signature Found, document is discarded");
        }
        DOMValidateContext valContext = new DOMValidateContext(publicKey, nl.item(0));
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
        XMLSignature signature = fac.unmarshalXMLSignature(valContext);
        return signature.validate(valContext);
    }

}
