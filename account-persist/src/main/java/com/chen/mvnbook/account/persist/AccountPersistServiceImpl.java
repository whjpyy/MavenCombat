package com.chen.mvnbook.account.persist;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;

/**
 * Created by YouZeng on 2015-09-10.
 */
public class AccountPersistServiceImpl implements AccountPersistService {
    private static final String ELEMENT_ROOT = "account-persist";
    private static final String ELEMENT_ACCOUNTS = "accounts";
    private static final String ELEMENT_ACCOUNT = "account";
    private static final String ELEMENT_ACCOUNT_ID = "id";
    private static final String ELEMENT_ACCOUNT_NAME = "name";
    private static final String ELEMENT_ACCOUNT_EMAIL = "email";
    private static final String ELEMENT_ACCOUNT_PASSWORD = "password";
    private static final String ELEMENT_ACCOUNT_ACTIVATED = "activated";

    private String file;
    private SAXReader reader = new SAXReader();

    private Document readDocument() throws AccountPersistException {
        File dataFile = new File(file);
        if(!dataFile.exists()){
            dataFile.getParentFile().mkdirs();
            Document document = DocumentFactory.getInstance().createDocument();
            Element rootEle = document.addElement(ELEMENT_ROOT);
            rootEle.addElement(ELEMENT_ACCOUNTS);
            writeDocument(document);
        }
        try{
            return reader.read(new File(file));
        } catch (DocumentException e) {
            throw new AccountPersistException("Unable to read persist data xml", e);
        }
    }

    private void writeDocument(Document doc) throws AccountPersistException {
        Writer out = null;
        try{
            out = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            XMLWriter writer = new XMLWriter(out, OutputFormat.createPrettyPrint());
            writer.write(doc);
        } catch (IOException e) {
            throw new AccountPersistException("Unable to write persist data xml", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    throw new AccountPersistException("Unable to close persist data xml", e);
                }
            }
        }
    }

    @Override
    public Account createAccount(Account account) throws AccountPersistException {
        Document document = readDocument();
        Element accountsEle = document.getRootElement().element(ELEMENT_ACCOUNTS);
        accountsEle.add(buildAccountElement(account));
        writeDocument(document);
        return account;
    }

    private Element buildAccountElement(Account account) {
        Element element = DocumentFactory.getInstance().createElement(ELEMENT_ACCOUNT);

        element.addElement(ELEMENT_ACCOUNT_ID).setText(account.getId());
        element.addElement(ELEMENT_ACCOUNT_NAME).setText(account.getName());
        element.addElement(ELEMENT_ACCOUNT_EMAIL).setText(account.getEmail());
        element.addElement(ELEMENT_ACCOUNT_PASSWORD).setText(account.getPassword());
        element.addElement(ELEMENT_ACCOUNT_ACTIVATED).setText(account.isActivated() ? "true" : "false");
        return element;
    }

    @Override
    public Account readAccount(String id) throws AccountPersistException {
        Document doc = readDocument();
        Element accountsEle = doc.getRootElement().element(ELEMENT_ACCOUNTS);
        for(Object object : accountsEle.elements()){
            Element accountEle = (Element)object;
            if(accountEle.elementText(ELEMENT_ACCOUNT_ID).equals(id)){
                return buildAccount(accountEle);
            }
        }
        return null;
    }

    private Account buildAccount(Element element) {
        Account account = new Account();
        account.setId(element.elementText(ELEMENT_ACCOUNT_ID));
        account.setName(element.elementText(ELEMENT_ACCOUNT_NAME));
        account.setEmail(element.elementText(ELEMENT_ACCOUNT_EMAIL));
        account.setPassword(element.elementText(ELEMENT_ACCOUNT_PASSWORD));
        account.setActivated("true".equals(element.elementText(ELEMENT_ACCOUNT_ACTIVATED)));
        return account;
    }

    @Override
    public Account updateAccount(Account account) throws AccountPersistException {
        if ( readAccount( account.getId() ) != null )
        {
            deleteAccount( account.getId() );

            return createAccount ( account );
        }

        return null;
    }

    @Override
    public void deleteAccount(String id) throws AccountPersistException {

    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
