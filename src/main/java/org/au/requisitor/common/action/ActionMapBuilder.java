package org.au.requisitor.common.action;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.au.requisitor.common.Status;

public class ActionMapBuilder {
	Map<Status, StatusActionBuilder> actions;
	
	public ActionMapBuilder() {
		actions = new HashMap<>();
	}
	
	public StatusActionBuilder addStatus(Status status) {
		if (!actions.containsKey(status)) {
			actions.put(status, new StatusActionBuilder(this));
		}
		return actions.get(status);
	}
	
	public List<ActionType> getActions(Status status) {
		if (actions.containsKey(status)) {
			return actions.get(status).getActions();
		} else {
			return new LinkedList<>();
		}
	}
	
	public static class StatusActionBuilder {
		ActionMapBuilder builder;
		List<ActionType> data;
		
		private StatusActionBuilder(ActionMapBuilder builder) {
			this.builder = builder;
			data = new LinkedList<ActionType>();
		}
		
		public StatusActionBuilder addAction(ActionType action) {
			data.add(action);
			return this;
		}
		
		public ActionMapBuilder addActions(ActionType ... actions) {
			for (ActionType action : actions) {
				data.add(action);
			}
			return builder;
		}
		
		public ActionMapBuilder fin() {
			return builder;
		}
		
		private List<ActionType> getActions() {
			return data;
		}
	}
}
