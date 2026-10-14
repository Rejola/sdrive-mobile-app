// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EstimateAdapter$EstimateViewHolder_ViewBinding implements Unbinder {
  private EstimateAdapter.EstimateViewHolder target;

  @UiThread
  public EstimateAdapter$EstimateViewHolder_ViewBinding(EstimateAdapter.EstimateViewHolder target,
      View source) {
    this.target = target;

    target.quantity = Utils.findRequiredViewAsType(source, R.id.quantityEditText, "field 'quantity'", EditText.class);
    target.rate = Utils.findRequiredViewAsType(source, R.id.amountEditText, "field 'rate'", EditText.class);
    target.amountTextView = Utils.findRequiredViewAsType(source, R.id.amountTextView, "field 'amountTextView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EstimateAdapter.EstimateViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.quantity = null;
    target.rate = null;
    target.amountTextView = null;
  }
}
