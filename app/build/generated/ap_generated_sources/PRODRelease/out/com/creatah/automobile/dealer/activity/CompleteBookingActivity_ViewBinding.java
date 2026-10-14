// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CompleteBookingActivity_ViewBinding implements Unbinder {
  private CompleteBookingActivity target;

  private View view2131296573;

  private View view2131296920;

  private View view2131296392;

  private View view2131296454;

  @UiThread
  public CompleteBookingActivity_ViewBinding(CompleteBookingActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CompleteBookingActivity_ViewBinding(final CompleteBookingActivity target, View source) {
    this.target = target;

    View view;
    target.searchEditText = Utils.findRequiredViewAsType(source, R.id.searchEditText, "field 'searchEditText'", EditText.class);
    target.searchIcon = Utils.findRequiredViewAsType(source, R.id.searchIcon, "field 'searchIcon'", AppCompatImageView.class);
    target.bottomSheet = Utils.findRequiredViewAsType(source, R.id.bottom_sheet, "field 'bottomSheet'", FrameLayout.class);
    view = Utils.findRequiredView(source, R.id.fromDateTextView, "field 'fromDateTextView' and method 'onSelectFromDate'");
    target.fromDateTextView = Utils.castView(view, R.id.fromDateTextView, "field 'fromDateTextView'", TextView.class);
    view2131296573 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectFromDate();
      }
    });
    view = Utils.findRequiredView(source, R.id.toDateTextView, "field 'toDateTextView' and method 'onSelectToDate'");
    target.toDateTextView = Utils.castView(view, R.id.toDateTextView, "field 'toDateTextView'", TextView.class);
    view2131296920 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSelectToDate();
      }
    });
    view = Utils.findRequiredView(source, R.id.applyFilter, "field 'applyFilter' and method 'onApplyFilter'");
    target.applyFilter = Utils.castView(view, R.id.applyFilter, "field 'applyFilter'", Button.class);
    view2131296392 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onApplyFilter();
      }
    });
    view = Utils.findRequiredView(source, R.id.clearButton, "field 'clearButton' and method 'onClearFilter'");
    target.clearButton = Utils.castView(view, R.id.clearButton, "field 'clearButton'", Button.class);
    view2131296454 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClearFilter();
      }
    });
    target.footerLayout = Utils.findRequiredViewAsType(source, R.id.footerLayout, "field 'footerLayout'", LinearLayout.class);
    target.serviceLayout = Utils.findRequiredViewAsType(source, R.id.serviceLayout, "field 'serviceLayout'", LinearLayout.class);
    target.searchLayout = Utils.findRequiredViewAsType(source, R.id.searchLayout, "field 'searchLayout'", LinearLayout.class);
    target.noJObCardFound = Utils.findRequiredViewAsType(source, R.id.noJObCardFound, "field 'noJObCardFound'", LinearLayout.class);
    target.completedServiceLayout = Utils.findRequiredViewAsType(source, R.id.completedServiceLayout, "field 'completedServiceLayout'", LinearLayout.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.retryButton = Utils.findRequiredViewAsType(source, R.id.retryButton, "field 'retryButton'", Button.class);
    target.noJobCardText = Utils.findRequiredViewAsType(source, R.id.noJobCardText, "field 'noJobCardText'", TextView.class);
    target.progressLayout = Utils.findRequiredViewAsType(source, R.id.progressLayout, "field 'progressLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CompleteBookingActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.searchEditText = null;
    target.searchIcon = null;
    target.bottomSheet = null;
    target.fromDateTextView = null;
    target.toDateTextView = null;
    target.applyFilter = null;
    target.clearButton = null;
    target.footerLayout = null;
    target.serviceLayout = null;
    target.searchLayout = null;
    target.noJObCardFound = null;
    target.completedServiceLayout = null;
    target.swipeRefreshLayout = null;
    target.retryButton = null;
    target.noJobCardText = null;
    target.progressLayout = null;

    view2131296573.setOnClickListener(null);
    view2131296573 = null;
    view2131296920.setOnClickListener(null);
    view2131296920 = null;
    view2131296392.setOnClickListener(null);
    view2131296392 = null;
    view2131296454.setOnClickListener(null);
    view2131296454 = null;
  }
}
