// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WorkCheckListActivity_ViewBinding implements Unbinder {
  private WorkCheckListActivity target;

  private View view2131296943;

  private View view2131296879;

  @UiThread
  public WorkCheckListActivity_ViewBinding(WorkCheckListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WorkCheckListActivity_ViewBinding(final WorkCheckListActivity target, View source) {
    this.target = target;

    View view;
    target.spareRecyclerView = Utils.findRequiredViewAsType(source, R.id.spareRecyclerView, "field 'spareRecyclerView'", RecyclerView.class);
    target.labourRecyclerView = Utils.findRequiredViewAsType(source, R.id.labourRecyclerView, "field 'labourRecyclerView'", RecyclerView.class);
    target.checkLayout = Utils.findRequiredViewAsType(source, R.id.checkLayout, "field 'checkLayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.updateButton, "field 'updateButton' and method 'onUpdatedClicked'");
    target.updateButton = Utils.castView(view, R.id.updateButton, "field 'updateButton'", Button.class);
    view2131296943 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onUpdatedClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.stockreqButton, "field 'stockreqButton' and method 'onUpdatedClickedCheck'");
    target.stockreqButton = Utils.castView(view, R.id.stockreqButton, "field 'stockreqButton'", Button.class);
    view2131296879 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onUpdatedClickedCheck();
      }
    });
    target.noResultFound = Utils.findRequiredViewAsType(source, R.id.noResultFound, "field 'noResultFound'", TextView.class);
    target.spareLayout = Utils.findRequiredViewAsType(source, R.id.addSpareLayout, "field 'spareLayout'", LinearLayout.class);
    target.labourLayout = Utils.findRequiredViewAsType(source, R.id.addLabourLayout, "field 'labourLayout'", LinearLayout.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.retryButton = Utils.findRequiredViewAsType(source, R.id.retryButton, "field 'retryButton'", Button.class);
    target.viewStatusBtn = Utils.findRequiredViewAsType(source, R.id.viewStatus, "field 'viewStatusBtn'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WorkCheckListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.spareRecyclerView = null;
    target.labourRecyclerView = null;
    target.checkLayout = null;
    target.updateButton = null;
    target.stockreqButton = null;
    target.noResultFound = null;
    target.spareLayout = null;
    target.labourLayout = null;
    target.swipeRefreshLayout = null;
    target.retryButton = null;
    target.viewStatusBtn = null;

    view2131296943.setOnClickListener(null);
    view2131296943 = null;
    view2131296879.setOnClickListener(null);
    view2131296879 = null;
  }
}
