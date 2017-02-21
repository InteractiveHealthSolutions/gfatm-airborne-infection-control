/*
Copyright(C) 2016 Interactive Health Solutions, Pvt. Ltd.
This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
 */
package com.ihsinformatics.gfaicweb.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedStackPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.ihsinformatics.gfaicweb.shared.CustomMessage;
import com.ihsinformatics.gfaicweb.shared.ErrorType;
import com.ihsinformatics.gfaicweb.shared.model.LocationAttributeClient;
import com.ihsinformatics.gfaicweb.shared.model.LocationAttributeTypeClient;
import com.ihsinformatics.gfaicweb.shared.model.LocationClient;
import com.ihsinformatics.gfaicweb.shared.model.PrivilegeClient;

/**
 * @author babar.anis@ihsinformatics.com
 */
public class LocationComposite extends AbstractComposite {

	final GreetingServiceAsync webService = GWT.create(GreetingService.class);

	SimplePanel gridPanel = new SimplePanel();

	VerticalPanel mainPanel = new VerticalPanel();
	VerticalPanel centerVerticalPanel = new VerticalPanel();
	VerticalPanel allLocationsPanel = new VerticalPanel();
	VerticalPanel deleteLocationPanel = new VerticalPanel();
	VerticalPanel locationAttributePanel = new VerticalPanel();

	HorizontalPanel deleteConfirmationPanel = new HorizontalPanel();
	HorizontalPanel locationFilterPanel = new HorizontalPanel();
	HorizontalPanel securityPanel = new HorizontalPanel();
	HorizontalPanel searchLocationPanel = new HorizontalPanel();
	HorizontalPanel locationAttributeHorizontalPanel = new HorizontalPanel();

	FlexTable topFlexTable = new FlexTable();
	FlexTable locationFlexTable = new FlexTable();
	FlexTable locationAttributesFlexTable = new FlexTable();
	FlexTable centerFlexTable = new FlexTable();
	FlexTable bottomFlexTable = new FlexTable();
	FlexTable locationAttributeFlexTable = new FlexTable();
	FlexTable filterTable = new FlexTable();

	ScrollPanel gridScrollPanel = new ScrollPanel();
	ScrollPanel scrollPanel = new ScrollPanel();
	Grid locationAttributesGrid = new Grid(2, 3);
	Grid grid = new Grid(1, 5);

	TabLayoutPanel formTabLayoutPanel = new TabLayoutPanel(2.0, Unit.EM);
	DecoratedStackPanel formStackPanel = new DecoratedStackPanel();

	Label formTitleLabel = new Label("Locations");
	Label locationIdLabel = new Label("Location ID:");
	Label locationNameLabel = new Label("Location Name:");
	Label categoryLabel = new Label("Category:");
	Label descriptionLabel = new Label("Description:");
	Label address1Label = new Label("Address 1:");
	Label address2Label = new Label("Address 2:");
	Label address3Label = new Label("Address 3");
	Label cityVillageLabel = new Label("City Village:");
	Label stateProvinceLabel = new Label("State Province:");
	Label countryLabel = new Label("Country:");
	Label landmark1Label = new Label("Landmark 1:");
	Label landmark2Label = new Label("Landmark 2:");
	Label latitudeLabel = new Label("Latitude:");
	Label longitudeLabel = new Label("Longitude:");
	Label primaryContactLabel = new Label("Primary Contact:");
	Label secondaryContactLabel = new Label("Secondary Contact:");
	Label emailLabel = new Label("Email:");
	Label faxLabel = new Label("Fax:");
	Label parentLocationLabel = new Label("Parent Location:");
	Label locationAttributeIdLabel = new Label("Location attribute ID:");
	Label locationAttributeIdValue = new Label("1");
	Label attributeTypeIdLabel = new Label("Attribute Type ID:");
	Label attributeLocationIdLabel = new Label("Location ID:");
	Label attributeValueLabel = new Label("Attribute Value:");
	Label lblLocationNameLabel = new Label("Location Name");
	Label lblLocationCountryLabel = new Label("Country Name");
	Label lblLocationParentLabel = new Label("Parent Location");
	Label addLocationAttribute = new Label("Add new Location Attribute");
	Label content = new Label();

	final DialogBox dialog = new DialogBox(false, true);

	TextBox locationNameTextBox = new TextBox();
	TextBox descriptionTextBox = new TextBox();
	TextBox address1TextBox = new TextBox();
	TextBox address2TextBox = new TextBox();
	TextBox address3TextBox = new TextBox();
	TextBox cityVillageTextBox = new TextBox();
	TextBox stateProvinceTextBox = new TextBox();
	TextBox landmark1TextBox = new TextBox();
	TextBox landmark2TextBox = new TextBox();
	TextBox latitudeTextBox = new TextBox();
	TextBox longitudeTextBox = new TextBox();
	TextBox primaryContactTextBox = new TextBox();
	TextBox secondaryContactTextbox = new TextBox();
	TextBox emailTextBox = new TextBox();
	TextBox faxTextBox = new TextBox();
	TextBox attributeTypeIdTextBox = new TextBox();
	TextBox attributeLocationIdTextBox = new TextBox();
	TextBox attributeValueTextBox = new TextBox();
	TextBox locationIdFilterTextBox = new TextBox();
	TextBox locationNameFilterTextBox = new TextBox();
	TextBox locationIdTextBox = new TextBox();

	ListBox categoryListBox = new ListBox();
	ListBox countryListBox = new ListBox();
	ListBox parentLocationListBox = new ListBox();
	ListBox filterParentLocationListBox = new ListBox();
	ListBox filterCountryListBox = new ListBox();
	ListBox NameCountryOptionListBox = new ListBox();
	ListBox CountryParentOptionListBox = new ListBox();

	Button saveButton = new Button("Save");
	Button clearButton = new Button("Clear");
	Button deleteButton = new Button("Delete");
	Button closeButton = new Button("Close");
	Button addLocationAttributeButton = new Button("+");
	Button yes = new Button("YES");
	Button no = new Button("NO");
	Button filterButton = new Button("Filter");
	Button searchLocationButton = new Button("Search Location");

	ToggleButton createButton = new ToggleButton("Create New");

	CellTable<LocationClient> dataCellTable = new CellTable<LocationClient>();
	List<LocationClient> location = new ArrayList<LocationClient>();
	LocationClient current = null;
	List<String> countryList = new ArrayList<String>(Arrays.asList("India",
			"Bangladesh", "UAE", "Pakistan", "Singapore"));
	List<String> categoryList = new ArrayList<String>(Arrays.asList("Office",
			"Resident", "Warehouse", "Hospital"));
	List<String> parentLocationList = new ArrayList<String>();
	private List<LocationAttributeClient> getExistingLocationrAttribute = new ArrayList<LocationAttributeClient>();
	List<String> attributeNames = new ArrayList<String>();

	List<LocationAttributeTypeClient> attributeList = new ArrayList<LocationAttributeTypeClient>();
	int rowCount = 0;

	public LocationComposite() {
		TBR.setCurrentForm("LOCATION");
		webService
				.getCurrentUserPrivileges(new AsyncCallback<List<PrivilegeClient>>() {

					@Override
					public void onSuccess(List<PrivilegeClient> result) {
						for (PrivilegeClient privilege : result) {
							if (privilege.getPrivilege().equals(
									"CREATE " + TBR.getCurrentFormName())) {
								canCreate = true;
							} else if (privilege.getPrivilege().equals(
									"EDIT " + TBR.getCurrentFormName())) {
								canEdit = true;
							} else if (privilege.getPrivilege().equals(
									"DELETE " + TBR.getCurrentFormName())) {
								canDelete = true;
							} else if (privilege.getPrivilege().equals(
									"VIEW " + TBR.getCurrentFormName())) {
								canView = true;
							}
						}
						createButton.setEnabled(canCreate);
						saveButton.setEnabled(canEdit);
						deleteButton.setEnabled(canDelete);
						if (!(canView | canCreate)) {
							formStackPanel.setVisible(false);
							Window.alert("Sorry, current user does not have privileges to View this form.");
							close();
						}

					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Unable to fetch Current User Privileges");

					}
				});
		initWidget(mainPanel);
		mainPanel.setSize("900px", "100%");
		mainPanel.add(topFlexTable);
		formTitleLabel.setStyleName("title");
		topFlexTable.setWidget(0, 0, formTitleLabel);
		mainPanel.add(centerVerticalPanel);
		centerVerticalPanel.setSize("100%", "100%");
		formTabLayoutPanel.setAnimationDuration(500);
		formTabLayoutPanel.setAnimationVertical(true);
		formTabLayoutPanel.add(formStackPanel, "Form View", false);
		formStackPanel.setWidth("100%");
		ScrollPanel scrollPanel = new ScrollPanel(locationFlexTable);
		scrollPanel.setSize("100%", "265px");
		formStackPanel.add(scrollPanel, "Location Information", false);
		locationFlexTable.setStyleName("FlexTable");
		locationFlexTable.setSize("100%", "100%");
		locationFlexTable.setWidget(0, 0, locationIdLabel);
		locationIdLabel.setStyleName("label");
		searchLocationPanel.add(locationIdTextBox);
		locationIdTextBox.setStyleName("textbox");
		locationIdTextBox.setWidth("55px");
		locationIdTextBox.setMaxLength(6);
		searchLocationPanel.add(searchLocationButton);
		searchLocationPanel.setSpacing(5);
		searchLocationButton.setStyleName("search-location-button");
		locationFlexTable.setWidget(0, 1, searchLocationPanel);
		locationFlexTable.setWidget(1, 0, locationNameLabel);
		locationNameLabel.setStyleName("label");
		locationFlexTable.setWidget(1, 1, locationNameTextBox);
		locationNameTextBox.setStyleName("textbox");
		locationFlexTable.setWidget(2, 0, categoryLabel);
		categoryLabel.setStyleName("label");
		for (String item : categoryList) {
			categoryListBox.addItem(item);
		}
		locationFlexTable.setWidget(2, 1, categoryListBox);
		locationFlexTable.setWidget(3, 0, descriptionLabel);
		descriptionLabel.setStyleName("label");
		locationFlexTable.setWidget(3, 1, descriptionTextBox);
		descriptionTextBox.setStyleName("textbox");
		locationFlexTable.setWidget(4, 0, address1Label);
		address1Label.setStyleName("label");
		locationFlexTable.setWidget(4, 1, address1TextBox);
		address1TextBox.setStyleName("textbox");
		locationFlexTable.setWidget(5, 0, address2Label);
		address2Label.setStyleName("label");
		locationFlexTable.setWidget(5, 1, address2TextBox);
		address2TextBox.setStyleName("textbox");
		locationFlexTable.setWidget(6, 0, address3Label);
		address3Label.setStyleName("label");
		locationFlexTable.setWidget(6, 1, address3TextBox);
		address3TextBox.setStyleName("textbox");
		locationFlexTable.setWidget(7, 0, cityVillageLabel);
		cityVillageLabel.setStyleName("label");
		locationFlexTable.setWidget(7, 1, cityVillageTextBox);
		cityVillageTextBox.setStyleName("textbox");
		locationFlexTable.setWidget(8, 0, stateProvinceLabel);
		stateProvinceLabel.setStyleName("label");
		locationFlexTable.setWidget(8, 1, stateProvinceTextBox);
		stateProvinceTextBox.setStyleName("textbox");
		locationFlexTable.setWidget(9, 0, countryLabel);
		countryLabel.setStyleName("label");
		filterCountryListBox.addItem("");
		filterParentLocationListBox.addItem("");
		for (String eachcountry : countryList) {
			countryListBox.addItem(eachcountry);
			filterCountryListBox.addItem(eachcountry);
		}
		locationFlexTable.setWidget(9, 1, countryListBox);
		locationFlexTable.setWidget(10, 0, landmark1Label);
		landmark1Label.setStyleName("label");
		locationFlexTable.setWidget(10, 1, landmark1TextBox);
		landmark1TextBox.setStyleName("textbox");
		locationFlexTable.setWidget(11, 0, landmark2Label);
		landmark2Label.setStyleName("label");
		locationFlexTable.setWidget(11, 1, landmark2TextBox);
		landmark2TextBox.setStyleName("textbox");
		locationFlexTable.setWidget(12, 0, latitudeLabel);
		latitudeLabel.setStyleName("label");
		locationFlexTable.setWidget(12, 1, latitudeTextBox);
		latitudeTextBox.setStyleName("textbox");
		locationFlexTable.setWidget(13, 0, longitudeLabel);
		longitudeLabel.setStyleName("label");
		locationFlexTable.setWidget(13, 1, longitudeTextBox);
		longitudeTextBox.setStyleName("textbox");
		locationFlexTable.setWidget(14, 0, primaryContactLabel);
		primaryContactLabel.setStyleName("label");
		locationFlexTable.setWidget(14, 1, primaryContactTextBox);
		primaryContactTextBox.setStyleName("textbox");
		locationFlexTable.setWidget(15, 0, secondaryContactLabel);
		secondaryContactLabel.setStyleName("label");
		locationFlexTable.setWidget(15, 1, secondaryContactTextbox);
		secondaryContactTextbox.setStyleName("textbox");
		locationFlexTable.setWidget(16, 0, emailLabel);
		emailLabel.setStyleName("label");
		locationFlexTable.setWidget(16, 1, emailTextBox);
		emailTextBox.setStyleName("textbox");
		locationFlexTable.setWidget(17, 0, faxLabel);
		faxLabel.setStyleName("label");
		locationFlexTable.setWidget(17, 1, faxTextBox);
		faxTextBox.setStyleName("textbox");
		locationFlexTable.setWidget(18, 0, parentLocationLabel);
		parentLocationLabel.setStyleName("label");
		locationFlexTable.setWidget(18, 1, parentLocationListBox);
		webService.getAllLocations(new AsyncCallback<List<LocationClient>>() {

			@Override
			public void onSuccess(List<LocationClient> result) {
				for (LocationClient item : result) {
					parentLocationList.add(item.getLocationName());
					parentLocationListBox.addItem(item.getLocationName());
					filterParentLocationListBox.addItem(item.getLocationName());
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Unable to parent Location");
			}
		});
		dialog.setText("Confirmation");
		deleteLocationPanel.add(content);
		deleteLocationPanel.add(deleteConfirmationPanel);
		deleteConfirmationPanel.add(yes);
		deleteConfirmationPanel.add(no);
		deleteConfirmationPanel.setSpacing(15);
		deleteConfirmationPanel.setWidth("60%");
		deleteLocationPanel.setCellHorizontalAlignment(deleteConfirmationPanel,
				HasHorizontalAlignment.ALIGN_CENTER);
		deleteLocationPanel.setSpacing(15);
		dialog.setWidget(deleteLocationPanel);
		ScrollPanel scrollPanel2 = new ScrollPanel(locationAttributeFlexTable);
		scrollPanel2.setSize("100%", "256px");
		formStackPanel.add(scrollPanel2, "Location Attribute", false);
		locationAttributeHorizontalPanel.add(addLocationAttributeButton);
		locationAttributeHorizontalPanel.add(addLocationAttribute);
		addLocationAttributeButton.setStyleName("operation-button");
		locationAttributeFlexTable.setWidget(0, 0,
				locationAttributeHorizontalPanel);
		addLocationAttribute.setStyleName("sub-label2");
		locationAttributeFlexTable.setWidget(1, 0, locationAttributePanel);
		webService
				.getAllLocationAttribute(new AsyncCallback<List<LocationAttributeTypeClient>>() {

					@Override
					public void onSuccess(
							List<LocationAttributeTypeClient> result) {
						for (LocationAttributeTypeClient item : result) {
							attributeList.add(item);
							attributeNames.add(item.getAttributeName());
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Unable to fetch All Location Attribute");
					}
				});
		locationAttributesFlexTable.setSize("100%", "100%");
		addLocationAttributeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (current == null && !createButton.isDown()) {
					Window.alert("Please select Location first to add UserAttribute");
				} else {
					HorizontalPanel newPanel = new HorizontalPanel();
					ListBox attributeListBox = new ListBox();
					for (LocationAttributeTypeClient item2 : attributeList) {
						attributeListBox.addItem(item2.getAttributeName());
					}
					TextBox attributeValueTextBox = new TextBox();
					Button deleteRow = new Button("X");
					deleteRow.setTitle(String.valueOf(rowCount));
					newPanel.setSpacing(5);
					newPanel.add(attributeListBox);
					newPanel.add(attributeValueTextBox);
					newPanel.add(deleteRow);
					locationAttributePanel.add(newPanel);
					rowCount++;

					deleteRow.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							Button button = (Button) event.getSource();
							for (int i = 0; i < locationAttributePanel
									.getWidgetCount(); i++) {
								HorizontalPanel horizontal = (HorizontalPanel) locationAttributePanel
										.getWidget(i);
								Button thisButton = (Button) horizontal
										.getWidget(2);
								if (thisButton.getTitle().equals(
										button.getTitle())) {
									locationAttributePanel.remove(i);
								}
							}
						}
					});

				}
			}
		});
		formTabLayoutPanel.add(gridPanel, "Grid View", false);
		gridPanel.setWidget(gridScrollPanel);
		gridScrollPanel.setSize("100%", "100%");
		gridScrollPanel.setWidget(allLocationsPanel);
		allLocationsPanel.setSize("100%", "100%");
		allLocationsPanel.add(grid);
		lblLocationCountryLabel.setStyleName("label");
		filterTable.setWidget(0, 0, lblLocationNameLabel);
		lblLocationNameLabel.setStyleName("label");
		filterTable.setWidget(0, 2, lblLocationCountryLabel);
		lblLocationCountryLabel.setStyleName("label");
		filterTable.setWidget(0, 4, lblLocationParentLabel);
		lblLocationParentLabel.setStyleName("label");
		filterTable.setWidget(1, 0, locationNameFilterTextBox);
		locationNameFilterTextBox.setVisibleLength(10);
		locationNameFilterTextBox.setStyleName("textbox");
		locationNameFilterTextBox.setMaxLength(40);
		filterTable.setWidget(1, 1, NameCountryOptionListBox);
		filterTable.setWidget(1, 2, filterCountryListBox);
		filterTable.setWidget(1, 3, CountryParentOptionListBox);
		filterTable.setWidget(1, 4, filterParentLocationListBox);
		filterTable.setWidget(1, 5, filterButton);
		filterButton.setStyleName("search-button");
		HTMLTable.CellFormatter formatter2 = filterTable.getCellFormatter();
		formatter2.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		formatter2
				.setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter2.setHorizontalAlignment(0, 2,
				HasHorizontalAlignment.ALIGN_CENTER);
		formatter2
				.setVerticalAlignment(0, 2, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter2.setHorizontalAlignment(0, 4,
				HasHorizontalAlignment.ALIGN_CENTER);
		formatter2
				.setVerticalAlignment(0, 4, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter2.setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		formatter2
				.setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter2.setHorizontalAlignment(1, 2,
				HasHorizontalAlignment.ALIGN_CENTER);
		formatter2
				.setVerticalAlignment(1, 2, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter2.setHorizontalAlignment(1, 4,
				HasHorizontalAlignment.ALIGN_CENTER);
		formatter2
				.setVerticalAlignment(1, 4, HasVerticalAlignment.ALIGN_MIDDLE);
		NameCountryOptionListBox.addItem("AND");
		NameCountryOptionListBox.addItem("OR");

		CountryParentOptionListBox.addItem("AND");
		CountryParentOptionListBox.addItem("OR");
		grid.setSize("850px", "20px");
		grid.setWidget(0, 0, filterTable);
		dataCellTable.setPageSize(10);
		allLocationsPanel.add(dataCellTable);
		dataCellTable.setSize("100%", "100%");
		centerVerticalPanel.add(formTabLayoutPanel);
		formTabLayoutPanel.setHeight("500px");
		mainPanel.add(centerFlexTable);
		centerFlexTable.setWidth("100%");
		mainPanel.add(bottomFlexTable);
		bottomFlexTable.setWidth("100%");
		createButton.setStyleName("toggle-button");
		bottomFlexTable.setWidget(0, 0, createButton);
		createButton.setWidth("80%");
		saveButton.setStyleName("submit-button");
		bottomFlexTable.setWidget(0, 1, saveButton);
		saveButton.setWidth("80%");
		deleteButton.setStyleName("submit-button");

		bottomFlexTable.setWidget(0, 2, deleteButton);
		deleteButton.setWidth("80%");
		clearButton.setStyleName("submit-button");
		bottomFlexTable.setWidget(0, 3, clearButton);
		clearButton.setWidth("80%");
		closeButton.setStyleName("submit-button");
		bottomFlexTable.setWidget(0, 4, closeButton);
		closeButton.setWidth("80%");
		init();
	}

	/**
	 * Additional initialization. Events, Lists, Grids, Defaults, etc.
	 */
	public void init() {
		createButton.addClickHandler(this);
		saveButton.addClickHandler(this);
		clearButton.addClickHandler(this);
		deleteButton.addClickHandler(this);
		closeButton.addClickHandler(this);
		filterButton.addClickHandler(this);
		searchLocationButton.addClickHandler(this);
		yes.addClickHandler(this);
		no.addClickHandler(this);
		createColumns();
		fill();
	}

	private void createColumns() {
		TextColumn<LocationClient> locationIdTextColumn = new TextColumn<LocationClient>() {

			@Override
			public String getValue(LocationClient object) {
				return object.getLocationId().toString();
			}

		};
		locationIdTextColumn.setSortable(true);
		dataCellTable.addColumn(locationIdTextColumn, "LocationID");

		TextColumn<LocationClient> locationNameEditTextColumn = new TextColumn<LocationClient>() {

			@Override
			public String getValue(LocationClient object) {
				return (String) object.getLocationName();
			}

		};
		locationNameEditTextColumn.setSortable(true);
		dataCellTable.addColumn(locationNameEditTextColumn, "Location Name");

		TextColumn<LocationClient> categoryEditTextColumn = new TextColumn<LocationClient>() {

			@Override
			public String getValue(LocationClient object) {
				return (String) object.getCategory();
			}
		};
		categoryEditTextColumn.setSortable(true);
		dataCellTable.addColumn(categoryEditTextColumn, "Category");

		TextColumn<LocationClient> descriptionEditTextColumn = new TextColumn<LocationClient>() {

			public String getValue(LocationClient object) {
				return (String) object.getDescription();
			}

		};

		descriptionEditTextColumn.setSortable(true);
		dataCellTable.addColumn(descriptionEditTextColumn, "Description");

		TextColumn<LocationClient> address1EditTextColumn = new TextColumn<LocationClient>() {
			@Override
			public String getValue(LocationClient object) {
				return (String) object.getAddress1();
			}
		};
		address1EditTextColumn.setSortable(true);
		dataCellTable.addColumn(address1EditTextColumn, "Address 1");

		TextColumn<LocationClient> address2EditTextColumn = new TextColumn<LocationClient>() {
			@Override
			public String getValue(LocationClient object) {
				return (String) object.getAddress2();
			}
		};
		address2EditTextColumn.setSortable(true);
		dataCellTable.addColumn(address2EditTextColumn, "Address 2");

		TextColumn<LocationClient> address3EditTextColumn = new TextColumn<LocationClient>() {
			@Override
			public String getValue(LocationClient object) {
				return (String) object.getAddress3();
			}
		};
		address3EditTextColumn.setSortable(true);
		dataCellTable.addColumn(address3EditTextColumn, "Address 3");

		TextColumn<LocationClient> cityVillageEditTextColumn = new TextColumn<LocationClient>() {
			@Override
			public String getValue(LocationClient object) {
				return (String) object.getCityVillage();
			}
		};
		cityVillageEditTextColumn.setSortable(true);
		dataCellTable.addColumn(cityVillageEditTextColumn, "City Village");

		TextColumn<LocationClient> stateProvinceEditTextColumn = new TextColumn<LocationClient>() {
			@Override
			public String getValue(LocationClient object) {
				return (String) object.getStateProvince();
			}
		};
		stateProvinceEditTextColumn.setSortable(true);
		dataCellTable.addColumn(stateProvinceEditTextColumn, "State Province");

		TextColumn<LocationClient> countryEditTextColumn = new TextColumn<LocationClient>() {
			@Override
			public String getValue(LocationClient object) {
				return (String) object.getCountry();
			}
		};
		countryEditTextColumn.setSortable(true);
		dataCellTable.addColumn(countryEditTextColumn, "Country");

		TextColumn<LocationClient> landmark1EditTextColumn = new TextColumn<LocationClient>() {
			@Override
			public String getValue(LocationClient object) {
				return (String) object.getLandmark1();
			}
		};
		landmark1EditTextColumn.setSortable(true);
		dataCellTable.addColumn(landmark1EditTextColumn, "Landmark 1");

		TextColumn<LocationClient> landmark2EditTextColumn = new TextColumn<LocationClient>() {
			@Override
			public String getValue(LocationClient object) {
				return (String) object.getLandmark2();
			}
		};
		landmark2EditTextColumn.setSortable(true);
		dataCellTable.addColumn(landmark2EditTextColumn, "Landmark 2");

		TextColumn<LocationClient> latitudeEditTextColumn = new TextColumn<LocationClient>() {
			@Override
			public String getValue(LocationClient object) {
				return (String) object.getLatitude();
			}
		};
		latitudeEditTextColumn.setSortable(true);
		dataCellTable.addColumn(latitudeEditTextColumn, "Latitude");

		TextColumn<LocationClient> longitudeEditTextColumn = new TextColumn<LocationClient>() {
			@Override
			public String getValue(LocationClient object) {
				return (String) object.getLongitude();
			}
		};
		longitudeEditTextColumn.setSortable(true);
		dataCellTable.addColumn(longitudeEditTextColumn, "Longitude");

		TextColumn<LocationClient> primaryContactEditTextColumn = new TextColumn<LocationClient>() {
			@Override
			public String getValue(LocationClient object) {
				return (String) object.getPrimaryContact();
			}
		};
		primaryContactEditTextColumn.setSortable(true);
		dataCellTable
				.addColumn(primaryContactEditTextColumn, "Primary Contact");

		TextColumn<LocationClient> secondaryContactEditTextColumn = new TextColumn<LocationClient>() {
			@Override
			public String getValue(LocationClient object) {
				return (String) object.getSecondaryContact();
			}
		};
		secondaryContactEditTextColumn.setSortable(true);
		dataCellTable.addColumn(secondaryContactEditTextColumn,
				"Secondary Contact");

		TextColumn<LocationClient> emailEditTextColumn = new TextColumn<LocationClient>() {
			@Override
			public String getValue(LocationClient object) {
				return (String) object.getEmail();
			}
		};
		emailEditTextColumn.setSortable(true);
		dataCellTable.addColumn(emailEditTextColumn, "Email");

		TextColumn<LocationClient> faxEditTextColumn = new TextColumn<LocationClient>() {
			@Override
			public String getValue(LocationClient object) {
				return (String) object.getFax();
			}
		};
		faxEditTextColumn.setSortable(true);
		dataCellTable.addColumn(faxEditTextColumn, "Fax");
		dataCellTable.addCellPreviewHandler(new Handler<LocationClient>() {

			@Override
			public void onCellPreview(CellPreviewEvent<LocationClient> event) {
				if (event.getNativeEvent().getType().contains("click")) {
					locationIdTextBox.setText(event.getValue().getLocationId()
							.toString());
					locationNameTextBox.setText(event.getValue()
							.getLocationName());
					categoryListBox.setSelectedIndex(categoryList.indexOf(event
							.getValue().getCategory()));
					descriptionTextBox.setText(event.getValue()
							.getDescription());
					address1TextBox.setText(event.getValue().getAddress1());
					address2TextBox.setText(event.getValue().getAddress2());
					address3TextBox.setText(event.getValue().getAddress3());
					cityVillageTextBox.setText(event.getValue()
							.getCityVillage());
					stateProvinceTextBox.setText(event.getValue()
							.getStateProvince());
					countryListBox.setSelectedIndex(countryList.indexOf(event
							.getValue().getCountry()));
					landmark1TextBox.setText(event.getValue().getLandmark1());
					landmark2TextBox.setText(event.getValue().getLandmark2());
					latitudeTextBox.setText(event.getValue().getLatitude());
					longitudeTextBox.setText(event.getValue().getLongitude());
					primaryContactTextBox.setText(event.getValue()
							.getPrimaryContact());
					secondaryContactTextbox.setText(event.getValue()
							.getSecondaryContact());
					emailTextBox.setText(event.getValue().getEmail());
					faxTextBox.setText(event.getValue().getFax());
					if (event.getValue().getLocationByParentLocation() != null) {
						parentLocationListBox
								.setSelectedIndex(parentLocationList
										.indexOf(event.getValue()
												.getLocationByParentLocation()
												.getLocationName()));
					}
					current = event.getValue();
					webService.getLocationAttribute(current,
							new AsyncCallback<List<LocationAttributeClient>>() {

								@Override
								public void onSuccess(
										List<LocationAttributeClient> result) {
									locationAttributePanel.clear();
									for (LocationAttributeClient item : result) {
										ListBox attributeListBox = new ListBox();
										TextBox attributeValueTextBox = new TextBox();
										Button deleteRow = new Button("X");
										for (LocationAttributeTypeClient item2 : attributeList) {
											attributeListBox.addItem(item2
													.getAttributeName());
										}
										getExistingLocationrAttribute.add(item);
										attributeListBox
												.setSelectedIndex(attributeNames
														.indexOf(item
																.getLocationAttributeType()
																.getAttributeName()));
										attributeValueTextBox.setText(item
												.getAttributeValue());
										HorizontalPanel newPanel = new HorizontalPanel();
										deleteRow.setTitle(String
												.valueOf(rowCount));
										newPanel.setSpacing(5);
										newPanel.add(attributeListBox);
										newPanel.add(attributeValueTextBox);
										newPanel.add(deleteRow);
										locationAttributePanel.add(newPanel);
										rowCount++;
										deleteRow
												.addClickHandler(new ClickHandler() {

													@Override
													public void onClick(
															ClickEvent event) {
														Button button = (Button) event
																.getSource();
														for (int i = 0; i < locationAttributePanel
																.getWidgetCount(); i++) {
															HorizontalPanel horizontal = (HorizontalPanel) locationAttributePanel
																	.getWidget(i);
															Button thisButton = (Button) horizontal
																	.getWidget(2);
															if (thisButton
																	.getTitle()
																	.equals(button
																			.getTitle())) {
																locationAttributePanel
																		.remove(i);
															}
														}
													}
												});
									}

								}

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Unable to fetch Location Attribute for current Location");
								}
							});

				}

			}
		});

	}

	public void setCurrent() {
		locationIdTextBox.setText(current.getLocationId().toString());
		locationNameTextBox.setValue(current.getLocationName());
		categoryListBox.setValue(0, current.getCategory());
		descriptionTextBox.setValue(current.getDescription());
		address1TextBox.setValue(current.getAddress1());
		address2TextBox.setValue(current.getAddress2());
		address3TextBox.setValue(current.getAddress3());
		cityVillageTextBox.setValue(current.getCityVillage());
		stateProvinceTextBox.setValue(current.getStateProvince());
		countryListBox.setValue(0, current.getCountry());
		landmark1TextBox.setValue(current.getLandmark1());
		landmark2TextBox.setValue(current.getLandmark2());
		latitudeTextBox.setValue(current.getLatitude());
		longitudeTextBox.setValue(current.getLongitude());
		primaryContactTextBox.setValue(current.getPrimaryContact());
		secondaryContactTextbox.setValue(current.getSecondaryContact());
		emailTextBox.setValue(current.getEmail());
		faxTextBox.setValue(current.getFax());
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget source = (Widget) event.getSource();
		if (source == createButton) {
			clear(locationFlexTable);
			locationIdTextBox.setText(null);
			locationIdTextBox.setVisible(false);
			searchLocationButton.setVisible(false);
			locationAttributePanel.clear();
			clear(locationAttributeFlexTable);
		}
		if (!createButton.isDown()) {
			locationIdTextBox.setVisible(true);
			searchLocationButton.setVisible(true);
		}
		if (source == saveButton) {
			if (createButton.isDown()) {
				save();
			} else {
				if (!locationIdTextBox.getText().isEmpty()) {
					update();
					clear();
				} else {
					Window.alert("ERROR, Please select Location first");
				}
			}
		} else if (source == clearButton) {
			clear();
			current = null;
		} else if (source == deleteButton) {
			content.setText("Are you sure you want to delete Location : "
					+ current.getLocationName() + "?");
			dialog.setPopupPosition(500, 250);
			dialog.show();
		} else if (source == filterButton) {
			if (!locationNameFilterTextBox.getText().equals("")) {
				if (!filterCountryListBox.getItemText(
						filterCountryListBox.getSelectedIndex()).equals("")) {
					if (!filterParentLocationListBox.getItemText(
							filterParentLocationListBox.getSelectedIndex())
							.equalsIgnoreCase("")) {
						if (NameCountryOptionListBox.getItemText(
								NameCountryOptionListBox.getSelectedIndex())
								.equalsIgnoreCase("AND")) {
							if (CountryParentOptionListBox.getItemText(
									CountryParentOptionListBox
											.getSelectedIndex())
									.equalsIgnoreCase("AND")) {
								webService
										.getLocationByNameAndCountryAndParent(
												locationNameFilterTextBox
														.getText()
														.toLowerCase(),
												filterCountryListBox
														.getItemText(
																filterCountryListBox
																		.getSelectedIndex())
														.toLowerCase(),
												filterParentLocationListBox
														.getItemText(
																filterParentLocationListBox
																		.getSelectedIndex())
														.toLowerCase(),
												new AsyncCallback<List<LocationClient>>() {

													@Override
													public void onSuccess(
															List<LocationClient> result) {
														location = result;
														dataCellTable.flush();
														if (location.size() > 0) {
															current = location
																	.get(0);
															dataCellTable
																	.setRowData(location);
														} else {
															Window.alert("No Location found");
														}
													}

													@Override
													public void onFailure(
															Throwable caught) {
														Window.alert("Unable to filter Location");
													}
												});
							} else {
								webService
										.getLocationByNameAndCountryorParent(
												locationNameFilterTextBox
														.getText()
														.toLowerCase(),
												filterCountryListBox
														.getItemText(
																filterCountryListBox
																		.getSelectedIndex())
														.toLowerCase(),
												filterParentLocationListBox
														.getItemText(
																filterParentLocationListBox
																		.getSelectedIndex())
														.toLowerCase(),
												new AsyncCallback<List<LocationClient>>() {

													@Override
													public void onSuccess(
															List<LocationClient> result) {
														location = result;
														dataCellTable.flush();
														if (location.size() > 0) {
															current = location
																	.get(0);
															dataCellTable
																	.setRowData(location);
														} else {
															Window.alert("No Location found");
														}
													}

													@Override
													public void onFailure(
															Throwable caught) {
														Window.alert("Unable to filter Location");
													}
												});
							}
						} else {
							if (CountryParentOptionListBox.getItemText(
									CountryParentOptionListBox
											.getSelectedIndex())
									.equalsIgnoreCase("AND")) {
								webService
										.getLocationByNameOrCountryAndParent(
												locationNameFilterTextBox
														.getText()
														.toLowerCase(),
												filterCountryListBox
														.getItemText(
																filterCountryListBox
																		.getSelectedIndex())
														.toLowerCase(),
												filterParentLocationListBox
														.getItemText(
																filterParentLocationListBox
																		.getSelectedIndex())
														.toLowerCase(),
												new AsyncCallback<List<LocationClient>>() {

													@Override
													public void onSuccess(
															List<LocationClient> result) {
														location = result;
														dataCellTable.flush();
														if (location.size() > 0) {
															current = location
																	.get(0);
															dataCellTable
																	.setRowData(location);
														} else {
															Window.alert("No Location found");
														}
													}

													@Override
													public void onFailure(
															Throwable caught) {
														Window.alert("Unable to filter Location");
													}
												});
							} else {
								webService
										.getLocationByNameOrCountryOrParent(
												locationNameFilterTextBox
														.getText()
														.toLowerCase(),
												filterCountryListBox
														.getItemText(
																filterCountryListBox
																		.getSelectedIndex())
														.toLowerCase(),
												filterParentLocationListBox
														.getItemText(
																filterParentLocationListBox
																		.getSelectedIndex())
														.toLowerCase(),
												new AsyncCallback<List<LocationClient>>() {

													@Override
													public void onSuccess(
															List<LocationClient> result) {
														location = result;
														dataCellTable.flush();
														if (location.size() > 0) {
															current = location
																	.get(0);
															dataCellTable
																	.setRowData(location);
														} else {
															Window.alert("No Location found");
														}
													}

													@Override
													public void onFailure(
															Throwable caught) {
														Window.alert("Unable to filter Location");
													}
												});
							}
						}
					} else if (NameCountryOptionListBox.getItemText(
							NameCountryOptionListBox.getSelectedIndex())
							.equalsIgnoreCase("AND")) {
						webService.getLocationByNameAndCountry(
								locationNameFilterTextBox.getText(),
								filterCountryListBox
										.getItemText(
												filterCountryListBox
														.getSelectedIndex())
										.toLowerCase(),
								new AsyncCallback<List<LocationClient>>() {

									@Override
									public void onSuccess(
											List<LocationClient> result) {
										location = result;
										dataCellTable.flush();
										if (location.size() > 0) {
											current = location.get(0);
											dataCellTable.setRowData(location);
										} else {
											Window.alert("No Location found with Name : "
													+ locationNameFilterTextBox
															.getText()
													+ " and Country : "
													+ filterCountryListBox
															.getItemText(filterCountryListBox
																	.getSelectedIndex()));
										}
									}

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Unable to filter Location");
									}
								});
					} else {
						webService.getLocationByNameOrCountry(
								locationNameFilterTextBox.getText(),
								filterCountryListBox
										.getItemText(
												filterCountryListBox
														.getSelectedIndex())
										.toLowerCase(),
								new AsyncCallback<List<LocationClient>>() {

									@Override
									public void onSuccess(
											List<LocationClient> result) {
										location = result;
										dataCellTable.flush();
										if (location.size() > 0) {
											current = location.get(0);
											dataCellTable.setRowData(location);
										} else {
											Window.alert("No Location found with Name : "
													+ locationNameFilterTextBox
															.getText()
													+ " or Country : "
													+ filterCountryListBox
															.getItemText(filterCountryListBox
																	.getSelectedIndex()));
										}
									}

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Unable to filter Location");
									}
								});
					}
				} else if (!filterParentLocationListBox.getItemText(
						filterParentLocationListBox.getSelectedIndex()).equals(
						"")) {
					if (CountryParentOptionListBox.getItemText(
							CountryParentOptionListBox.getSelectedIndex())
							.equalsIgnoreCase("AND")) {
						webService.getLocationByNameAndParent(
								locationNameFilterTextBox.getText()
										.toLowerCase(),
								filterParentLocationListBox.getItemText(
										filterParentLocationListBox
												.getSelectedIndex())
										.toLowerCase(),
								new AsyncCallback<List<LocationClient>>() {

									@Override
									public void onSuccess(
											List<LocationClient> result) {
										location = result;
										dataCellTable.flush();
										if (location.size() > 0) {
											current = location.get(0);
											dataCellTable.setRowData(location);
										} else {
											Window.alert("No Location found with Name : "
													+ locationNameFilterTextBox
															.getText()
													+ " and ParentLocation : "
													+ filterParentLocationListBox
															.getItemText(filterParentLocationListBox
																	.getSelectedIndex()));
										}

									}

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Unable to Filter Location");
									}
								});
					} else {
						webService.getLocationByNameOrParent(
								locationNameFilterTextBox.getText()
										.toLowerCase(),
								filterParentLocationListBox.getItemText(
										filterParentLocationListBox
												.getSelectedIndex())
										.toLowerCase(),
								new AsyncCallback<List<LocationClient>>() {

									@Override
									public void onSuccess(
											List<LocationClient> result) {
										location = result;
										dataCellTable.flush();
										if (location.size() > 0) {
											current = location.get(0);
											dataCellTable.setRowData(location);
										} else {
											Window.alert("No Location found with Name : "
													+ locationNameFilterTextBox
															.getText()
													+ " or ParentLocation : "
													+ filterParentLocationListBox
															.getItemText(filterParentLocationListBox
																	.getSelectedIndex()));
										}

									}

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Unable to Filter Location");
									}
								});
					}
				} else {
					webService.filterLocationByLocationName(
							locationNameFilterTextBox.getText(),
							new AsyncCallback<List<LocationClient>>() {

								@Override
								public void onSuccess(
										List<LocationClient> result) {
									location = result;
									dataCellTable.flush();
									if (location.size() > 0) {
										current = location.get(0);
										dataCellTable.setRowData(location);
									} else {
										Window.alert("No locaiton found with name : "
												+ locationNameFilterTextBox
														.getText());
									}

								}

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Unable to filter Location");
								}
							});
				}
			} else if (!filterCountryListBox.getItemText(
					filterCountryListBox.getSelectedIndex()).equals("")) {
				if (!filterParentLocationListBox.getItemText(
						filterParentLocationListBox.getSelectedIndex())
						.equalsIgnoreCase("")) {
					if (CountryParentOptionListBox.getItemText(
							CountryParentOptionListBox.getSelectedIndex())
							.equalsIgnoreCase("AND")) {
						webService.getLocationByCountryAndParent(
								filterCountryListBox
										.getItemText(
												filterCountryListBox
														.getSelectedIndex())
										.toLowerCase(),
								filterParentLocationListBox.getItemText(
										filterParentLocationListBox
												.getSelectedIndex())
										.toLowerCase(),
								new AsyncCallback<List<LocationClient>>() {

									@Override
									public void onSuccess(
											List<LocationClient> result) {
										location = result;
										dataCellTable.flush();
										if (location.size() > 0) {
											current = location.get(0);
											dataCellTable.setRowData(location);
										} else {
											Window.alert("No Location found with Country : "
													+ filterCountryListBox
															.getItemText(filterCountryListBox
																	.getSelectedIndex())
													+ " and ParentLocation: "
													+ filterParentLocationListBox
															.getItemText(filterParentLocationListBox
																	.getSelectedIndex()));
										}

									}

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Unable to filter Location");
									}
								});
					} else {
						webService.getLocationByCountryOrParent(
								filterCountryListBox
										.getItemText(
												filterCountryListBox
														.getSelectedIndex())
										.toLowerCase(),
								filterParentLocationListBox.getItemText(
										filterParentLocationListBox
												.getSelectedIndex())
										.toLowerCase(),
								new AsyncCallback<List<LocationClient>>() {

									@Override
									public void onSuccess(
											List<LocationClient> result) {
										location = result;
										dataCellTable.flush();
										if (location.size() > 0) {
											current = location.get(0);
											dataCellTable.setRowData(location);
										} else {
											Window.alert("No Location found with Country : "
													+ filterCountryListBox
															.getItemText(filterCountryListBox
																	.getSelectedIndex())
													+ " or ParentLocation"
													+ filterParentLocationListBox
															.getItemText(filterParentLocationListBox
																	.getSelectedIndex()));
										}

									}

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Unable to filter Location");
									}
								});
					}
				} else {
					webService.getLocationByCountry(
							filterCountryListBox.getItemText(
									filterCountryListBox.getSelectedIndex())
									.toLowerCase(),
							new AsyncCallback<List<LocationClient>>() {

								@Override
								public void onSuccess(
										List<LocationClient> result) {
									location = result;
									dataCellTable.flush();
									if (location.size() > 0) {
										current = location.get(0);
										dataCellTable.setRowData(location);
									} else {
										Window.alert("No locaiton found with Country : "
												+ filterCountryListBox
														.getItemText(filterCountryListBox
																.getSelectedIndex()));
									}
								}

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Unable to filter Location");
								}
							});
				}
			} else if (!filterParentLocationListBox.getItemText(
					filterParentLocationListBox.getSelectedIndex()).equals("")) {
				webService.getLocationByParentLocation(
						filterParentLocationListBox.getItemText(
								filterParentLocationListBox.getSelectedIndex())
								.toLowerCase(),
						new AsyncCallback<List<LocationClient>>() {

							@Override
							public void onSuccess(List<LocationClient> result) {
								location = result;
								dataCellTable.flush();
								if (location.size() > 0) {
									current = location.get(0);
									dataCellTable.setRowData(location);
								} else {
									Window.alert("No location found with Parent : "
											+ filterParentLocationListBox
													.getItemText(filterParentLocationListBox
															.getSelectedIndex()));
								}
							}

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Unable to filter Location");
							}
						});
			} else {
				fill();
			}
		} else if (source == closeButton) {
			close();
		} else if (source == searchLocationButton) {
			if (!locationIdTextBox.getText().equals("")) {
				webService.getLocationById(
						Integer.parseInt(locationIdTextBox.getText()),
						new AsyncCallback<LocationClient>() {

							@Override
							public void onSuccess(LocationClient result) {
								current = result;
								locationIdTextBox.setText(result
										.getLocationId().toString());
								locationNameTextBox.setText(result
										.getLocationName());
								categoryListBox.setSelectedIndex(categoryList
										.indexOf(result.getCategory()));
								descriptionTextBox.setText(result
										.getDescription());
								address1TextBox.setText(result.getAddress1());
								address2TextBox.setText(result.getAddress2());
								address3TextBox.setText(result.getAddress3());
								cityVillageTextBox.setText(result
										.getCityVillage());
								stateProvinceTextBox.setText(result
										.getStateProvince());
								countryListBox.setSelectedIndex(countryList
										.indexOf(result.getCountry()));
								landmark1TextBox.setText(result.getLandmark1());
								landmark2TextBox.setText(result.getLandmark2());
								latitudeTextBox.setText(result.getLatitude());
								longitudeTextBox.setText(result.getLongitude());
								primaryContactTextBox.setText(result
										.getPrimaryContact());
								secondaryContactTextbox.setText(result
										.getSecondaryContact());
								emailTextBox.setText(result.getEmail());
								faxTextBox.setText(result.getFax());
								if (result.getLocationByParentLocation() != null) {
									parentLocationListBox
											.setSelectedIndex(parentLocationList
													.indexOf(result
															.getLocationByParentLocation()
															.getLocationName()));
								}
								webService
										.getLocationAttribute(
												current,
												new AsyncCallback<List<LocationAttributeClient>>() {

													@Override
													public void onSuccess(
															List<LocationAttributeClient> result) {
														locationAttributePanel
																.clear();
														for (LocationAttributeClient item : result) {
															ListBox attributeListBox = new ListBox();
															TextBox attributeValueTextBox = new TextBox();
															Button deleteRow = new Button(
																	"X");
															for (LocationAttributeTypeClient item2 : attributeList) {
																attributeListBox
																		.addItem(item2
																				.getAttributeName());
															}
															getExistingLocationrAttribute
																	.add(item);
															attributeListBox
																	.setSelectedIndex(attributeNames
																			.indexOf(item
																					.getLocationAttributeType()
																					.getAttributeName()));
															attributeValueTextBox
																	.setText(item
																			.getAttributeValue());
															HorizontalPanel newPanel = new HorizontalPanel();
															deleteRow
																	.setTitle(String
																			.valueOf(rowCount));
															newPanel.setSpacing(5);
															newPanel.add(attributeListBox);
															newPanel.add(attributeValueTextBox);
															newPanel.add(deleteRow);
															locationAttributePanel
																	.add(newPanel);
															rowCount++;
															deleteRow
																	.addClickHandler(new ClickHandler() {

																		@Override
																		public void onClick(
																				ClickEvent event) {
																			Button button = (Button) event
																					.getSource();
																			for (int i = 0; i < locationAttributePanel
																					.getWidgetCount(); i++) {
																				HorizontalPanel horizontal = (HorizontalPanel) locationAttributePanel
																						.getWidget(i);
																				Button thisButton = (Button) horizontal
																						.getWidget(2);
																				if (thisButton
																						.getTitle()
																						.equals(button
																								.getTitle())) {
																					locationAttributePanel
																							.remove(i);
																				}
																			}
																		}
																	});
														}

													}

													@Override
													public void onFailure(
															Throwable caught) {
														Window.alert("Unable to fetch Location Attribute for current Location");
													}
												});
							}

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("No location found with ID : "
										+ locationIdTextBox.getText());
							}
						});
			}
		} else if (source == yes) {
			dialog.hide();
			delete();
		} else if (source == no) {
			dialog.hide();
		}
	}

	@Override
	public void fill() {
		webService.getAllLocations(new AsyncCallback<List<LocationClient>>() {

			@Override
			public void onSuccess(List<LocationClient> result) {
				location = result;
				dataCellTable.setRowData(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Unable to fetch location in GridView");
			}
		});
	}

	@Override
	public boolean validate() {
		final StringBuilder errorMessage = new StringBuilder();
		boolean valid = true;
		/* Validate mandatory fields */
		if (locationNameTextBox.getText().equals("")) {
			errorMessage.append(CustomMessage
					.getErrorMessage(ErrorType.INVALID_DATA_ERROR) + "\n");
			valid = false;
		}
		if (!valid) {
			Window.alert(errorMessage.toString());
		}
		if (!checkDuplicateUserAttribute()) {
			Window.alert("Duplicate UserAttribute, Please select Unique User Attribute");
			valid = false;
		}
		if (!IsEmailValid(emailTextBox.getText())) {
			Window.alert("Invalid Email. Please enter valid email Address");
			valid = false;
		}

		return valid;
	}

	@Override
	public void setPrivileges() {
	}

	@Override
	public void save() {
		if (validate()) {
			LocationClient newLocation = new LocationClient();
			newLocation.setLocationName(locationNameTextBox.getText());
			newLocation.setCategory(categoryListBox.getItemText(categoryListBox
					.getSelectedIndex()));
			newLocation.setDescription(descriptionTextBox.getText());
			newLocation.setAddress1(address1TextBox.getText());
			newLocation.setAddress2(address2TextBox.getText());
			newLocation.setAddress3(address3TextBox.getText());
			newLocation.setCityVillage(cityVillageTextBox.getText());
			newLocation.setStateProvince(stateProvinceTextBox.getText());
			newLocation.setCountry(countryListBox.getItemText(countryListBox
					.getSelectedIndex()));
			newLocation.setLandmark1(landmark1TextBox.getText());
			newLocation.setLandmark2(landmark2TextBox.getText());
			newLocation.setLatitude(latitudeTextBox.getText());
			newLocation.setLongitude(longitudeTextBox.getText());
			newLocation.setPrimaryContact(primaryContactTextBox.getText());
			newLocation.setSecondaryContact(secondaryContactTextbox.getText());
			newLocation.setEmail(emailTextBox.getText());
			newLocation.setFax(faxTextBox.getText());
			webService.saveLocation(newLocation, parentLocationListBox
					.getItemText(parentLocationListBox.getSelectedIndex()),
					new AsyncCallback<LocationClient>() {

						@Override
						public void onSuccess(LocationClient result) {
							for (int i = 0; i < locationAttributePanel
									.getWidgetCount(); i++) {
								HorizontalPanel horizontal = (HorizontalPanel) locationAttributePanel
										.getWidget(i);
								ListBox thisListBox = (ListBox) horizontal
										.getWidget(0);
								TextBox thisTextBox = (TextBox) horizontal
										.getWidget(1);
								String attributeName = thisListBox
										.getItemText(thisListBox
												.getSelectedIndex());
								String attributeValue = thisTextBox.getText();
								webService.saveLocationAttribute(attributeName,
										attributeValue, result,
										new AsyncCallback<Void>() {

											@Override
											public void onSuccess(Void result) {
												System.out
														.println("LocationAttribute has been Added suceessfully");
											}

											@Override
											public void onFailure(
													Throwable caught) {
												Window.alert("Unable to save UserAttribute");
											}
										});
								Window.alert("Location has been saved Successfully");
							}
						}

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Unable to save Location");
						}
					});
		}
	}

	@Override
	public void update() {
		if (validate()) {
			final LocationClient locationClient = new LocationClient();
			locationClient.setLocationId(Integer.parseInt(locationIdTextBox
					.getText()));
			locationClient.setLocationName(locationNameTextBox.getText());
			locationClient.setCategory(categoryListBox
					.getItemText(categoryListBox.getSelectedIndex()));
			locationClient.setDescription(descriptionTextBox.getText());
			locationClient.setAddress1(address1TextBox.getText());
			locationClient.setAddress2(address2TextBox.getText());
			locationClient.setAddress3(address3TextBox.getText());
			locationClient.setCityVillage(cityVillageTextBox.getText());
			locationClient.setStateProvince(stateProvinceTextBox.getText());
			locationClient.setCountry(countryListBox.getItemText(countryListBox
					.getSelectedIndex()));
			locationClient.setLandmark1(landmark1TextBox.getText());
			locationClient.setLandmark2(landmark2TextBox.getText());
			locationClient.setLatitude(latitudeTextBox.getText());
			locationClient.setLongitude(longitudeTextBox.getText());
			locationClient.setPrimaryContact(primaryContactTextBox.getText());
			locationClient.setSecondaryContact(secondaryContactTextbox
					.getText());
			locationClient.setEmail(emailTextBox.getText());
			locationClient.setFax(faxTextBox.getText());
			webService.updateLocation(locationClient, parentLocationListBox
					.getItemText(parentLocationListBox.getSelectedIndex()),
					new AsyncCallback<Void>() {

						@Override
						public void onSuccess(Void result) {
							webService.getLocationById(
									locationClient.getLocationId(),
									new AsyncCallback<LocationClient>() {

										@Override
										public void onSuccess(
												LocationClient result) {
											webService
													.deleteAllLocationAttributebyLocation(
															result,
															new AsyncCallback<Void>() {

																@Override
																public void onSuccess(
																		Void result) {
																	Window.alert("Deleted All Location Attributes");
																}

																@Override
																public void onFailure(
																		Throwable caught) {
																	Window.alert("Unable to Update Location Attributes");
																}
															});

											for (int i = 0; i < locationAttributePanel
													.getWidgetCount(); i++) {
												HorizontalPanel horizontal = (HorizontalPanel) locationAttributePanel
														.getWidget(i);
												ListBox thisListBox = (ListBox) horizontal
														.getWidget(0);
												TextBox thisTextBox = (TextBox) horizontal
														.getWidget(1);
												String attributeName = thisListBox
														.getItemText(thisListBox
																.getSelectedIndex());
												String attributeValue = thisTextBox
														.getText();
												webService
														.saveLocationAttribute(
																attributeName,
																attributeValue,
																result,
																new AsyncCallback<Void>() {

																	@Override
																	public void onSuccess(
																			Void result) {
																		System.out
																				.println("LocationAttribute has been Added suceessfully");
																	}

																	@Override
																	public void onFailure(
																			Throwable caught) {
																		Window.alert("Unable to save UserAttribute");
																	}
																});
											}
										}

										@Override
										public void onFailure(Throwable caught) {
											// TODO Auto-generated method stub

										}
									});
							Window.alert("Updated Location successfully");
						}

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Unable to update Location");
						}
					});
		}
	}

	@Override
	public void delete() {
		webService.deleteAllLocationAttributebyLocation(current,
				new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						webService.deleteLocation(current.getLocationId(),
								new AsyncCallback<Void>() {

									@Override
									public void onSuccess(Void result) {
										Window.alert("Successfully deleted Location");
									}

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Unable to delete Location");
									}
								});

					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Unable to delete Location Attribute");

					}
				});
		fill();
	}

	public void clear() {
		clear(locationFlexTable);
		locationAttributePanel.clear();
		clear(locationAttributeFlexTable);
	}

	public boolean checkDuplicateUserAttribute() {
		List<String> attributesList = new ArrayList<String>();
		for (int i = 0; i < locationAttributePanel.getWidgetCount(); i++) {
			HorizontalPanel horizontal = (HorizontalPanel) locationAttributePanel
					.getWidget(i);
			ListBox thisListBox = (ListBox) horizontal.getWidget(0);
			if (attributesList.contains(thisListBox.getItemText(thisListBox
					.getSelectedIndex()))) {
				return false;
			} else {
				attributesList.add(thisListBox.getItemText(thisListBox
						.getSelectedIndex()));
			}
		}
		return true;
	}

	public boolean IsEmailValid(String email) {
		int loc = email.indexOf("@");
		if (loc == -1) {
			return false;
		}
		int periodLoc = email.indexOf(".", loc);
		if (periodLoc - loc <= 1) {
			return false;
		}
		return true;
	}
}