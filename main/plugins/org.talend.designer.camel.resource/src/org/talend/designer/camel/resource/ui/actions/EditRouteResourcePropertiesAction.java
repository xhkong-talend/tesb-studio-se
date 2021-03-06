// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.camel.resource.ui.actions;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.talend.camel.designer.util.CamelRepositoryNodeType;
import org.talend.commons.runtime.model.repository.ERepositoryStatus;
import org.talend.commons.ui.runtime.image.EImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.designer.camel.resource.i18n.Messages;
import org.talend.designer.camel.resource.ui.wizards.EditRouteResourcePropertiesWizard;
import org.talend.metadata.managment.ui.wizard.PropertiesWizard;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.ui.actions.EditPropertiesAction;

/**
 * smallet class global comment. Detailled comment <br/>
 * 
 * $Id: talend.epf 1 2006-09-29 17:06:40 +0000 (ven., 29 sept. 2006) nrousseau $
 * 
 */
public class EditRouteResourcePropertiesAction extends EditPropertiesAction {

	public EditRouteResourcePropertiesAction() {
		super();
		this.setText(Messages
				.getString("EditRouteResourcePropertiesAction.Title")); //$NON-NLS-1$
		this.setToolTipText(Messages
				.getString("EditRouteResourcePropertiesAction.tooltip")); //$NON-NLS-1$
		this.setImageDescriptor(ImageProvider.getImageDesc(EImage.EDIT_ICON));
	}

	public void init(TreeViewer viewer, IStructuredSelection selection) {
		boolean canWork = selection.size() == 1;
		if (canWork) {
			Object o = ((IStructuredSelection) selection).getFirstElement();
			if (o instanceof RepositoryNode) {
				RepositoryNode node = (RepositoryNode) o;
				switch (node.getType()) {
				case REPOSITORY_ELEMENT:
					if (node.getObjectType() == CamelRepositoryNodeType.repositoryRouteResourceType) {
						canWork = true;
						break;
					}
				default:
					canWork = false;
					break;
				}
				if (canWork) {
					canWork = (node.getObject().getRepositoryStatus() != ERepositoryStatus.DELETED);
				}
				if (canWork) {
					canWork = isLastVersion(node);
				}
			}
		}
		setEnabled(canWork);
	}
	
	@Override
	protected PropertiesWizard getPropertiesWizard(
			IRepositoryViewObject object, IPath path) {
		return new EditRouteResourcePropertiesWizard(object, path, getNeededVersion() == null);
	}
}
