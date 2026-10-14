// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CheckListAdapter$CheckViewHolder_ViewBinding implements Unbinder {
  private CheckListAdapter.CheckViewHolder target;

  @UiThread
  public CheckListAdapter$CheckViewHolder_ViewBinding(CheckListAdapter.CheckViewHolder target,
      View source) {
    this.target = target;

    target.checkListLayout = Utils.findRequiredViewAsType(source, R.id.checkListLayout, "field 'checkListLayout'", LinearLayout.class);
    target.oneLayout = Utils.findRequiredViewAsType(source, R.id.oneLayout, "field 'oneLayout'", LinearLayout.class);
    target.twoLayout = Utils.findRequiredViewAsType(source, R.id.twoLayout, "field 'twoLayout'", LinearLayout.class);
    target.threeLayout = Utils.findRequiredViewAsType(source, R.id.threeLayout, "field 'threeLayout'", LinearLayout.class);
    target.fourLayout = Utils.findRequiredViewAsType(source, R.id.fourLayout, "field 'fourLayout'", LinearLayout.class);
    target.fiveLayout = Utils.findRequiredViewAsType(source, R.id.fiveLayout, "field 'fiveLayout'", LinearLayout.class);
    target.oneImageView = Utils.findRequiredViewAsType(source, R.id.oneImageView, "field 'oneImageView'", AppCompatImageView.class);
    target.twoImageView = Utils.findRequiredViewAsType(source, R.id.twoImageView, "field 'twoImageView'", AppCompatImageView.class);
    target.threeImageView = Utils.findRequiredViewAsType(source, R.id.threeImageView, "field 'threeImageView'", AppCompatImageView.class);
    target.fourImageView = Utils.findRequiredViewAsType(source, R.id.fourImageView, "field 'fourImageView'", AppCompatImageView.class);
    target.fiveImageView = Utils.findRequiredViewAsType(source, R.id.fiveImageView, "field 'fiveImageView'", AppCompatImageView.class);
    target.oneText = Utils.findRequiredViewAsType(source, R.id.oneText, "field 'oneText'", TextView.class);
    target.twoText = Utils.findRequiredViewAsType(source, R.id.twoText, "field 'twoText'", TextView.class);
    target.threeText = Utils.findRequiredViewAsType(source, R.id.threeText, "field 'threeText'", TextView.class);
    target.fourText = Utils.findRequiredViewAsType(source, R.id.fourText, "field 'fourText'", TextView.class);
    target.fiveText = Utils.findRequiredViewAsType(source, R.id.fiveText, "field 'fiveText'", TextView.class);
    target.titleTextView = Utils.findRequiredViewAsType(source, R.id.item, "field 'titleTextView'", TextView.class);
    target.checkBoxImageView = Utils.findRequiredViewAsType(source, R.id.checkboxImageView, "field 'checkBoxImageView'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CheckListAdapter.CheckViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.checkListLayout = null;
    target.oneLayout = null;
    target.twoLayout = null;
    target.threeLayout = null;
    target.fourLayout = null;
    target.fiveLayout = null;
    target.oneImageView = null;
    target.twoImageView = null;
    target.threeImageView = null;
    target.fourImageView = null;
    target.fiveImageView = null;
    target.oneText = null;
    target.twoText = null;
    target.threeText = null;
    target.fourText = null;
    target.fiveText = null;
    target.titleTextView = null;
    target.checkBoxImageView = null;
  }
}
