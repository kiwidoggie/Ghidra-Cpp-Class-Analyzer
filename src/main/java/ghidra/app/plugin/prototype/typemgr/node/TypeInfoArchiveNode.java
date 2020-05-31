package ghidra.app.plugin.prototype.typemgr.node;

import cppclassanalyzer.data.ClassTypeInfoManager;
import cppclassanalyzer.data.typeinfo.ClassTypeInfoDB;

public interface TypeInfoArchiveNode {

	ClassTypeInfoManager getTypeManager();
	void addNode(ClassTypeInfoDB type);

	TypeInfoNode getNode(ClassTypeInfoDB type);

	boolean isProgramNode();
}