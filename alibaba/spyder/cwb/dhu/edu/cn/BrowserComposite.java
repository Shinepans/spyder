package alibaba.spyder.cwb.dhu.edu.cn;

import java.util.ArrayList;  
import java.util.List;  
  
import org.apache.commons.lang.StringUtils;  
import org.eclipse.swt.SWT;  
import org.eclipse.swt.browser.Browser;  
import org.eclipse.swt.browser.CloseWindowListener;  
import org.eclipse.swt.browser.LocationAdapter;  
import org.eclipse.swt.browser.LocationEvent;  
import org.eclipse.swt.browser.OpenWindowListener;  
import org.eclipse.swt.browser.ProgressEvent;  
import org.eclipse.swt.browser.ProgressListener;  
import org.eclipse.swt.browser.StatusTextEvent;  
import org.eclipse.swt.browser.StatusTextListener;  
import org.eclipse.swt.browser.TitleEvent;  
import org.eclipse.swt.browser.TitleListener;  
import org.eclipse.swt.browser.WindowEvent;  
import org.eclipse.swt.custom.CLabel;  
import org.eclipse.swt.custom.CTabFolder;  
import org.eclipse.swt.custom.CTabItem;  
import org.eclipse.swt.events.KeyAdapter;  
import org.eclipse.swt.events.KeyEvent;  
import org.eclipse.swt.events.MouseAdapter;  
import org.eclipse.swt.events.MouseEvent;  
import org.eclipse.swt.events.SelectionAdapter;  
import org.eclipse.swt.events.SelectionEvent;  
import org.eclipse.swt.graphics.Point;  
import org.eclipse.swt.layout.FormAttachment;  
import org.eclipse.swt.layout.FormData;  
import org.eclipse.swt.layout.FormLayout;  
import org.eclipse.swt.layout.GridData;  
import org.eclipse.swt.layout.GridLayout;  
import org.eclipse.swt.widgets.Button;  
import org.eclipse.swt.widgets.Combo;  
import org.eclipse.swt.widgets.Composite;  
import org.eclipse.swt.widgets.Display;  
import org.eclipse.swt.widgets.Label;  
import org.eclipse.swt.widgets.ProgressBar;  
import org.eclipse.ui.forms.widgets.FormToolkit;  
  
import com.sinosoft.media360.server.domain.News_Document;  
import com.sinosoft.media360.util.NewActionMenu;  
import com.swtdesigner.SWTResourceManager;  
  
public class BrowserComposite extends Composite {  
    private static final String DEFAULT_BLANK_URL = "about:blank";  
    private static final String BROWSER_DATA_STATUS = "status";  
    private List<String> invalidTitleList = new ArrayList<String>();  
      
    private final static FormToolkit toolkit = new FormToolkit(Display.getCurrent());  
    private static Combo combUrl;  
    private CTabFolder browserFolder;  
    private CTabItem newTab;  
    private CLabel statusLabel;  
    private ProgressBar progressBar;  
      
    {  
        invalidTitleList.add(DEFAULT_BLANK_URL);  
        invalidTitleList.add("503 Service Unavailable");  
        invalidTitleList.add("导航已取消");  
        invalidTitleList.add("Internet Explorer 无法显示该网页");  
        invalidTitleList.add("无法显示此页");  
    }  
      
  
    /** 
     * Create the composite 
     *  
     * @param parent 
     * @param style 
     */  
    public BrowserComposite(Composite parent, int style) {  
        super(parent, style);  
        setLayout(new FormLayout());  
        toolkit.adapt(this);  
        toolkit.paintBordersFor(this);  
  
        final Composite composite = toolkit.createComposite(this, SWT.NO_TRIM | SWT.RESIZE);  
        final GridLayout gridLayout = new GridLayout();  
        gridLayout.horizontalSpacing = 2;  
        gridLayout.numColumns = 7;  
        composite.setLayout(gridLayout);  
        final FormData fd_composite = new FormData();  
        fd_composite.right = new FormAttachment(100, -5);  
        fd_composite.bottom = new FormAttachment(0, 40);  
        fd_composite.top = new FormAttachment(0, 0);  
        fd_composite.left = new FormAttachment(0, 5);  
        composite.setLayoutData(fd_composite);  
        toolkit.paintBordersFor(composite);  
  
        final Button newBt = new Button(composite, SWT.NONE);  
        newBt.setToolTipText("新建浏览器");  
        newBt.setImage(SWTResourceManager.getImage(BrowserComposite.class, "/tool_new.gif"));  
        newBt.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));  
        // 打开新的浏览器窗口  
        newBt.addSelectionListener(new SelectionAdapter() {  
            public void widgetSelected(final SelectionEvent e) {  
                openNewBrowserTab();  
            }  
        });  
        toolkit.adapt(newBt, true, true);  
  
        final Label label_1 = new Label(composite, SWT.SEPARATOR);  
        label_1.setLayoutData(new GridData(SWT.DEFAULT, 35));  
        toolkit.adapt(label_1, true, true);  
  
        ButtonSelectionListener listener = new ButtonSelectionListener();  
          
        final Button backBt = new Button(composite, SWT.NONE);  
        backBt.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));  
        backBt.setImage(SWTResourceManager.getImage(BrowserComposite.class, "/back.gif"));  
        backBt.addSelectionListener(listener);  
        backBt.setToolTipText("后退");  
        toolkit.adapt(backBt, true, true);  
  
        final Button forwardBt = new Button(composite, SWT.NONE);  
        forwardBt.setImage(SWTResourceManager.getImage(BrowserComposite.class, "/forward.gif"));  
        forwardBt.addSelectionListener(listener);  
        forwardBt.setToolTipText("前进");  
        forwardBt.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));  
        toolkit.adapt(forwardBt, true, true);  
  
        combUrl = new Combo(composite, SWT.BORDER);  
        combUrl.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));  
        combUrl.setVisibleItemCount(25);  
        combUrl.addSelectionListener(new SelectionAdapter() {  
            @Override  
            public void widgetSelected(SelectionEvent e) {  
                openEnteredUrl(false);  
            }  
        });  
        combUrl.addKeyListener(new KeyAdapter() {  
            // 按回车或数字键盘的Enter则浏览内容  
            public void keyPressed(final KeyEvent e) {  
                if(e.keyCode == SWT.CR || e.keyCode ==SWT.KEYPAD_CR){  
                    openEnteredUrl(true);  
                }  
            }  
        });  
        toolkit.adapt(combUrl, true, true);  
          
        final Button refreshBt = new Button(composite, SWT.NONE);  
        refreshBt.setImage(SWTResourceManager.getImage(BrowserComposite.class, "/tool_refresh.gif"));  
        refreshBt.addSelectionListener(listener);  
        refreshBt.setToolTipText("刷新");  
        refreshBt.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));  
        toolkit.adapt(refreshBt, true, true);  
          
        final Button stopBt = new Button(composite, SWT.NONE);  
        stopBt.setImage(SWTResourceManager.getImage(BrowserComposite.class, "/stop.gif"));  
        stopBt.addSelectionListener(listener);  
        stopBt.setToolTipText("停止");  
        stopBt.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));  
        toolkit.adapt(stopBt, true, true);  
  
        browserFolder = new CTabFolder(this, SWT.BORDER);  
        browserFolder.setMinimumCharacters(8);  
        browserFolder.setSelectionBackground(SWTResourceManager.getColor(227,238,251));  
        browserFolder.setTabHeight(25);  
        browserFolder.addSelectionListener(new SelectionAdapter() {  
            public void widgetSelected(final SelectionEvent e) {  
                if(e.item == newTab){  
                    openNewBrowserTab();    //新选项卡  
                }else{  
                    Browser browser = (Browser) ((CTabItem) e.item).getControl();  
                    if (browser != null ) {  
                        String url = browser.getUrl();  
                        if("".equals(url)){  
                            url = DEFAULT_BLANK_URL;  
                        }  
                        combUrl.setText(url);  
                        if (url.equals(DEFAULT_BLANK_URL)) {  
                            combUrl.setFocus();  
                        }  
                          
                        String status = (String) browser.getData(BROWSER_DATA_STATUS);  
                        if (status != null) {  
                            statusLabel.setText(status);  
                        }  
                          
                        progressBar.setVisible(false);  
                    }  
                }  
            }  
        });  
  
        browserFolder.addMouseListener(new MouseAdapter() {  
            // 双击关闭选中的浏览器窗口  
            public void mouseDoubleClick(final MouseEvent e) {  
                CTabItem tab = browserFolder.getItem(new Point(e.x, e.y));  
                if (tab != null && tab != newTab) {  
                    tab.dispose();  
                    if (browserFolder.getItemCount() == 1) {  
                        openNewBrowserTab();  
                    }  
                } else {  
                    openNewBrowserTab();  
                }  
            }  
        });  
        final FormData fd_tabFolder = new FormData();  
        fd_tabFolder.left = new FormAttachment(composite, 0, SWT.LEFT);  
        fd_tabFolder.right = new FormAttachment(composite, 0, SWT.RIGHT);  
        fd_tabFolder.top = new FormAttachment(composite, 5, SWT.BOTTOM);  
        browserFolder.setLayoutData(fd_tabFolder);  
        toolkit.adapt(browserFolder, true, true);  
          
        newTab = new CTabItem(browserFolder, SWT.NONE);  
//      newTab.setText("    ");  
        newTab.setImage(SWTResourceManager.getImage(BrowserComposite.class, "/newTab.jpg"));  
        newTab.setToolTipText("新选项卡");  
          
        statusLabel = new CLabel(this, SWT.NONE);  
        fd_tabFolder.bottom = new FormAttachment(statusLabel, -5, SWT.TOP);  
        final FormData fd_statusLabel = new FormData();  
        fd_statusLabel.left = new FormAttachment(browserFolder, 0, SWT.LEFT);  
        fd_statusLabel.right = new FormAttachment(100, -210);  
        fd_statusLabel.bottom = new FormAttachment(100, -5);  
        statusLabel.setLayoutData(fd_statusLabel);  
        toolkit.adapt(statusLabel, true, true);  
        statusLabel.setText("状态栏");  
  
        progressBar = new ProgressBar(this, SWT.NONE);  
        final FormData fd_progressBar = new FormData();  
        fd_progressBar.bottom = new FormAttachment(statusLabel, 0, SWT.BOTTOM);  
        fd_progressBar.left = new FormAttachment(100, -200);  
        fd_progressBar.right = new FormAttachment(browserFolder, 0, SWT.RIGHT);  
        progressBar.setLayoutData(fd_progressBar);  
        toolkit.adapt(progressBar, true, true);  
          
        openNewBrowserTab();  
    }  
      
    private void addUrlToCombo(String url){  
        if(!DEFAULT_BLANK_URL.equals(url)){  
            int index = combUrl.indexOf(url);  
            if(index > 0){  
                combUrl.remove(index);  
            }  
            if(index != 0){  
                combUrl.add(url, 0);  
            }  
            combUrl.select(0);  
            if(combUrl.getItemCount() > 50){  
                combUrl.remove(50, combUrl.getItemCount()-1);  
            }  
        }  
    }  
      
    /** 
     * 打开网页 
     * @param newTabItem 是否在新选项卡中打开 
     */  
    private void openEnteredUrl(boolean newTabItem) {  
        String url = combUrl.getText();  
        if (!("".equals(url) || DEFAULT_BLANK_URL.equals(url))) {  
            Browser browser = null;  
            CTabItem tabItem = browserFolder.getSelection();  
            String itemTitle = tabItem.getToolTipText();  
            if(newTabItem && !invalidTitleList.contains(itemTitle)){  
                browser = openNewBrowserTab();  
            }else{  
                browser = (Browser) tabItem.getControl();  
            }  
            browser.setUrl(url);  
            CTabItem item = ((CTabFolder)browser.getParent()).getSelection();  
            item.setText(StringUtils.abbreviate(url, 20));  
            item.setToolTipText(url);  
            addUrlToCombo(url);  
        }  
    }  
  
    /** 
     * 打开一个新选项卡 
     * @return 新选项卡中的Broser对象 
     */  
    private Browser openNewBrowserTab() {  
        final CTabItem browserTab = new CTabItem(browserFolder, SWT.NONE, browserFolder.getItemCount()-1);  
        browserTab.setShowClose(true);  
        browserTab.setImage(SWTResourceManager.getImage(BrowserComposite.class, "/ie.gif"));  
        browserFolder.setSelection(browserTab);  
          
        final Browser browser = new Browser(browserFolder, SWT.NONE);  
        initialize(browser);  
        browserTab.setText(DEFAULT_BLANK_URL);  
        browserTab.setToolTipText(DEFAULT_BLANK_URL);  
        combUrl.setText(DEFAULT_BLANK_URL);  
        combUrl.setFocus();  
        toolkit.adapt(browser, true, true);  
        browserTab.setControl(browser);  
        return browser;  
    }  
  
    /** register WindowEvent listeners */  
    private void initialize(final Browser browser) {  
        browser.addOpenWindowListener(new OpenWindowListener() {    //打开一个新的浏览器窗口事件  
            public void open(WindowEvent event) {  
                event.browser = openNewBrowserTab();  
            }  
        });  
  
        browser.addCloseWindowListener(new CloseWindowListener() {  //关闭浏览器事件  
            public void close(WindowEvent event) {  
                Browser browser = (Browser) event.widget;  
                ((CTabFolder)browser.getParent()).getSelection().dispose();  
            }  
        });  
        browser.addProgressListener(new ProgressListener() {    //装载网页事件  
            public void changed(ProgressEvent event) {  
                if (browserFolder.getSelection().getControl() == browser) {  
                    if (!progressBar.isVisible()) {  
                        progressBar.setVisible(true);  
                    }  
                    if (event.current < event.total) {  
                        progressBar.setMaximum(event.total);  
                        progressBar.setSelection(event.current);  
                    } else {  
                        progressBar.setVisible(false);  
                    }  
                }  
            }  
  
            public void completed(ProgressEvent event) {  
                if (browserFolder.getSelection().getControl() == browser)  
                    progressBar.setVisible(false);  
            }  
        });  
        browser.addStatusTextListener(new StatusTextListener() {    //状态栏改变事件  
            public void changed(StatusTextEvent event) {  
                browser.setData(BROWSER_DATA_STATUS, event.text);  
                if (browserFolder.getSelection().getControl() == browser) {  
                    statusLabel.setText(event.text);  
                }  
            }  
        });  
        browser.addTitleListener(new TitleListener() {  //标题改变事件  
            public void changed(TitleEvent event) {  
                CTabItem item = ((CTabFolder)browser.getParent()).getSelection();  
                item.setText(StringUtils.abbreviate(event.title, 20));  
                item.setToolTipText(event.title);  
            }  
        });  
          
          
        browser.addLocationListener(new LocationAdapter() { //为浏览器注册地址改变事件   
            @Override   
            public void changed(LocationEvent e) {  
                if(e.top){  
                    addUrlToCombo(e.location);  
                }  
            }   
        });  
          
          
        News_Document doc = new News_Document();  
        new NewActionMenu(browser, doc, true);  //设置右键  
    }  
      
    class ButtonSelectionListener extends SelectionAdapter{  
        @Override  
        public void widgetSelected(SelectionEvent e) {  
            String operationName = ((Button)e.widget).getToolTipText();  
            Browser browser = (Browser)browserFolder.getSelection().getControl();  
            if(browser != null){  
                if("后退".equals(operationName)){  
                    if(browser.isBackEnabled()){  
                        browser.back();  
                    }  
                }else if("前进".equals(operationName)){  
                    if(browser.isForwardEnabled()){  
                        browser.forward();  
                    }  
                }else if("刷新".equals(operationName)){  
                    browser.refresh();  
                }else if("停止".equals(operationName)){  
                    browser.stop();  
                }  
            }  
        }  
    }  
}  