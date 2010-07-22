/*******************************************************************************
 * Copyright (c) 2010 BestSolution.at and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tom Schindl <tom.schindl@bestsolution.at> - initial API and implementation
 ******************************************************************************/
package org.eclipse.e4.demo.mailapp;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.ObservablesManager;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.e4.demo.mailapp.mailservice.domain.IMail;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class MailView {
	private DataBindingContext dbc;
	private WritableValue mail = new WritableValue();
	private ObservablesManager manager;
	
	public MailView(final Composite composite) {
		dbc = new DataBindingContext();
		manager = new ObservablesManager();
		
		manager.runAndCollect(new Runnable() {
			
			public void run() {
				initUI(composite);
			}
		});
	}
	
	public void setMail(IMail mail) {
		if( mail != null ) {
			this.mail.setValue(mail);			
		}
	}
	
	private void initUI(Composite composite) {
		Composite parent = new Composite(composite, SWT.NONE);
		GridLayout gd = new GridLayout();
		gd.horizontalSpacing=0;
		gd.verticalSpacing=0;
		parent.setLayout(gd);
		
		Composite header = new Composite(parent,SWT.NONE);
		header.setLayout(new GridLayout(2,false));
		header.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label l = new Label(header, SWT.NONE);
		l.setText("From");
		
		l = new Label(header, SWT.NONE);
		l.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		dbc.bindValue(WidgetProperties.text().observe(l), BeanProperties.value("from").observeDetail(mail));
		
		l = new Label(header,SWT.NONE);
		l.setText("Subject");
		
		l = new Label(header, SWT.NONE);
		l.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		dbc.bindValue(WidgetProperties.text().observe(l), BeanProperties.value("subject").observeDetail(mail));
		
		l = new Label(header,SWT.NONE);
		l.setText("To");
		
		l = new Label(header, SWT.NONE);
		l.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		dbc.bindValue(WidgetProperties.text().observe(l), BeanProperties.value("to").observeDetail(mail));
		
		l = new Label(parent, SWT.SEPARATOR|SWT.HORIZONTAL);
		l.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Text t = new Text(parent, SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL|SWT.WRAP);
		t.setLayoutData(new GridData(GridData.FILL_BOTH));
		t.setEditable(false);
		dbc.bindValue(WidgetProperties.text().observe(t), BeanProperties.value("body").observeDetail(mail));
	}
}
