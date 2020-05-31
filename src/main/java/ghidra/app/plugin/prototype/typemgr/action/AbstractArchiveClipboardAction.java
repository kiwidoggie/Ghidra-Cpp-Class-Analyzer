package ghidra.app.plugin.prototype.typemgr.action;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.KeyStroke;

import ghidra.app.plugin.prototype.typemgr.TypeInfoArchiveGTree;
import ghidra.app.plugin.prototype.typemgr.node.TypeInfoArchiveNode;

import docking.ActionContext;
import docking.KeyBindingPrecedence;
import docking.action.KeyBindingData;
import docking.action.MenuData;
import docking.widgets.tree.GTree;
import docking.widgets.tree.GTreeNode;
import docking.widgets.tree.support.GTreeNodeTransferable;
import docking.widgets.tree.support.GTreeTransferHandler;

abstract class AbstractArchiveClipboardAction extends AbstractFileArchivePopupAction {

	private final Clipboard clipboard;

	AbstractArchiveClipboardAction(String name, int keyCode, TypeInfoArchiveHandler handler) {
		super(name, handler);
		this.clipboard = handler.getPlugin().getClipboard();
		setPopupMenuData(new MenuData(new String[] { name }, EDIT_GROUP));
		setKeyBindingData(new KeyBindingData(KeyStroke.getKeyStroke(keyCode,
			InputEvent.CTRL_DOWN_MASK), KeyBindingPrecedence.ActionMapLevel));
		setEnabled(true);
	}

	final List<GTreeNode> getSelectedRootTreeNodes(ActionContext context) {
		return getTree()
			.getSelectedNodes()
			.stream()
			.filter(TypeInfoArchiveNode.class::isInstance)
			.map(GTreeNode.class::cast)
			.collect(Collectors.toList());
	}

	final void setClipboardContents(GTree gTree, List<GTreeNode> nodes) {
		GTreeTransferHandler dragNDropHandler = gTree.getDragNDropHandler();
		Transferable contents = new GTreeNodeTransferable(dragNDropHandler, nodes);
		clipboard.setContents(contents, DummyClipboardOwner.DUMMY);
	}

	final List<GTreeNode> getClipboardContents() {
		Transferable contents = clipboard.getContents(this);
		if (contents instanceof GTreeNodeTransferable) {
			return ((GTreeNodeTransferable) contents).getAllData();
		}
		return Collections.emptyList();
	}

	final TypeInfoArchiveGTree getTree() {
		return getHandler().getTree();
	}

}