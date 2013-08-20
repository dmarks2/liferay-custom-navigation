package de.olafkock.liferay.navigation;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomNavigationPreAction extends Action {
	public CustomNavigationPreAction() {
		super();
	}

	public void run(HttpServletRequest request, HttpServletResponse response) throws ActionException {
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			final long scopeGroupId = themeDisplay.getScopeGroupId();
			long companyId = themeDisplay.getCompanyId();
			Layout layout = themeDisplay.getLayout();
			LinkedList<Layout> layouts = ExpandoNavigationUtil.getLayouts(
					scopeGroupId, layout.isPublicLayout(), companyId);
			if(! layouts.isEmpty()) {
				themeDisplay.setLayouts(layouts);
			}
		} catch (PortalException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		} catch (Exception ignore) {
			System.err.println("CustomNavigationPreAction: ignoring " + ignore.getClass().getName());
		}
	}

}