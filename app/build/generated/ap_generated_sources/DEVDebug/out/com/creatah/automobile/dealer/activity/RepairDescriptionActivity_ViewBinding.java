// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
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

public class RepairDescriptionActivity_ViewBinding implements Unbinder {
  private RepairDescriptionActivity target;

  private View view2131296360;

  private View view2131296368;

  @UiThread
  public RepairDescriptionActivity_ViewBinding(RepairDescriptionActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RepairDescriptionActivity_ViewBinding(final RepairDescriptionActivity target,
      View source) {
    this.target = target;

    View view;
    target.descriptionTextView = Utils.findRequiredViewAsType(source, R.id.descriptionTextView, "field 'descriptionTextView'", TextView.class);
    target.description1Layout = Utils.findRequiredViewAsType(source, R.id.description1Layout, "field 'description1Layout'", LinearLayout.class);
    target.noResultFound = Utils.findRequiredViewAsType(source, R.id.noResultFound, "field 'noResultFound'", TextView.class);
    target.descriptionLayout = Utils.findRequiredViewAsType(source, R.id.descriptionLayout, "field 'descriptionLayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.addLabourButton, "field 'addLabourButton' and method 'onAddLabour'");
    target.addLabourButton = Utils.castView(view, R.id.addLabourButton, "field 'addLabourButton'", Button.class);
    view2131296360 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onAddLabour();
      }
    });
    view = Utils.findRequiredView(source, R.id.addSpareButton, "field 'addSpareButton' and method 'onAddSpare'");
    target.addSpareButton = Utils.castView(view, R.id.addSpareButton, "field 'addSpareButton'", Button.class);
    view2131296368 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onAddSpare();
      }
    });
    target.labourRecyclerView = Utils.findRequiredViewAsType(source, R.id.labourRecyclerView, "field 'labourRecyclerView'", RecyclerView.class);
    target.spareRecyclerView = Utils.findRequiredViewAsType(source, R.id.spareRecyclerView, "field 'spareRecyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RepairDescriptionActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.descriptionTextView = null;
    target.description1Layout = null;
    target.noResultFound = null;
    target.descriptionLayout = null;
    target.addLabourButton = null;
    target.addSpareButton = null;
    target.labourRecyclerView = null;
    target.spareRecyclerView = null;

    view2131296360.setOnClickListener(null);
    view2131296360 = null;
    view2131296368.setOnClickListener(null);
    view2131296368 = null;
  }
}
