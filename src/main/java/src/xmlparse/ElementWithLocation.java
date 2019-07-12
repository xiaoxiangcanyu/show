package src.xmlparse;


import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.tree.DefaultElement;

public class ElementWithLocation extends DefaultElement {
	private int lineNum = 0, colNum = 0;

	public ElementWithLocation(QName qname) {
		super(qname);
		// TODO Auto-generated constructor stub
	}

	public ElementWithLocation(QName qname, int attrCount) {
		super(qname, attrCount);
	}

	public ElementWithLocation(String name) {
		super(name);
	}

	public ElementWithLocation(String name, Namespace namespace) {
		super(name, namespace);
	}

	public int getColumnNumber() {
		return this.colNum;
	}

	public int getLineNumber() {
		return this.lineNum;
	}

	public void setLocation(int lineNum, int colNum) {
		this.lineNum = lineNum;
		this.colNum = colNum;
	}
}
