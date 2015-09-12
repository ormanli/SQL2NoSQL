package com.sql2nosql.visitor;

import java.util.Date;

import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnalyticExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.CastExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.ExtractExpression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.IntervalExpression;
import net.sf.jsqlparser.expression.JdbcNamedParameter;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.JsonExpression;
import net.sf.jsqlparser.expression.KeepExpression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.NumericBind;
import net.sf.jsqlparser.expression.OracleHierarchicalExpression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.SignedExpression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.UserVariable;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.WithinGroupExpression;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Modulo;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.expression.operators.relational.RegExpMatchOperator;
import net.sf.jsqlparser.expression.operators.relational.RegExpMySQLOperator;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.SetStatement;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.execute.Execute;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.ValuesList;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;

import com.google.common.collect.Lists;
import com.sql2nosql.node.NoQuery;
import com.sql2nosql.node.Query;
import com.sql2nosql.node.column.NoQueryColumn;
import com.sql2nosql.node.table.NoQueryTable;
import com.sql2nosql.node.where.NoQueryCondition;
import com.sql2nosql.node.where.NoQueryConditionChain;
import com.sql2nosql.node.where.NoQueryConditionOperator;
import com.sql2nosql.node.where.NoQueryWhere;

public class SQLVisitor implements ExpressionVisitor, SelectVisitor,
		StatementVisitor, FromItemVisitor, SelectItemVisitor {

	private NoQueryColumn columns = new NoQueryColumn();
	private NoQueryTable tables = new NoQueryTable();
	private NoQueryWhere where = new NoQueryWhere();
	private boolean isSelect = true;

	public Query getNoQuery() {
		return new NoQuery(columns, tables, where);
	}

	@Override
	public void visit(Select select) {
		select.getSelectBody().accept(this);
	}

	@Override
	public void visit(Delete delete) {

	}

	@Override
	public void visit(Update update) {

	}

	@Override
	public void visit(Insert insert) {

	}

	@Override
	public void visit(Replace replace) {

	}

	@Override
	public void visit(Drop drop) {

	}

	@Override
	public void visit(Truncate truncate) {

	}

	@Override
	public void visit(CreateIndex createIndex) {

	}

	@Override
	public void visit(CreateTable createTable) {

	}

	@Override
	public void visit(CreateView createView) {

	}

	@Override
	public void visit(Alter alter) {

	}

	@Override
	public void visit(Statements stmts) {

	}

	@Override
	public void visit(Execute execute) {

	}

	@Override
	public void visit(SetStatement set) {

	}

	@Override
	public void visit(PlainSelect plainSelect) {
		plainSelect.getFromItem().accept(this);

		plainSelect.getSelectItems().forEach(item -> item.accept(this));

		isSelect = false;

		if (plainSelect.getWhere() != null) {
			plainSelect.getWhere().accept(this);
		}
	}

	@Override
	public void visit(SetOperationList setOpList) {

	}

	@Override
	public void visit(WithItem withItem) {

	}

	@Override
	public void visit(NullValue nullValue) {

	}

	@Override
	public void visit(Function function) {

	}

	@Override
	public void visit(SignedExpression signedExpression) {

	}

	@Override
	public void visit(JdbcParameter jdbcParameter) {

	}

	@Override
	public void visit(JdbcNamedParameter jdbcNamedParameter) {

	}

	@Override
	public void visit(DoubleValue doubleValue) {
		NoQueryCondition condition = where.peekCondition();
		condition.setValue(doubleValue.getValue());
	}

	@Override
	public void visit(LongValue longValue) {
		NoQueryCondition condition = where.peekCondition();
		condition.setValue(longValue.getValue());
	}

	@Override
	public void visit(DateValue dateValue) {
		NoQueryCondition condition = where.peekCondition();
		condition.setValue(new Date(dateValue.getValue().getTime()));
	}

	@Override
	public void visit(TimeValue timeValue) {
		NoQueryCondition condition = where.peekCondition();
		condition.setValue(timeValue.getValue());
	}

	@Override
	public void visit(TimestampValue timestampValue) {
		NoQueryCondition condition = where.peekCondition();
		condition.setValue(timestampValue.getValue());
	}

	@Override
	public void visit(Parenthesis parenthesis) {

	}

	@Override
	public void visit(StringValue stringValue) {
		NoQueryCondition condition = where.peekCondition();
		condition.setValue(stringValue.getValue());
	}

	@Override
	public void visit(Addition addition) {

	}

	@Override
	public void visit(Division division) {

	}

	@Override
	public void visit(Multiplication multiplication) {

	}

	@Override
	public void visit(Subtraction subtraction) {

	}

	private void visitExpression(BinaryExpression expression, NoQueryConditionChain chain, NoQueryConditionOperator operator) {
		expression.getLeftExpression().accept(this);
		expression.getRightExpression().accept(this);

		NoQueryCondition condition = where.peekCondition();
		if (chain != null) {
			condition.setChain(chain);
		}

		if (operator != null) {
			condition.setOperator(operator);
		}
	}

	@Override
	public void visit(AndExpression andExpression) {
		visitExpression(andExpression, NoQueryConditionChain.AND, null);
	}

	@Override
	public void visit(OrExpression orExpression) {
		visitExpression(orExpression, NoQueryConditionChain.OR, null);
	}

	@Override
	public void visit(Between between) {

	}

	@Override
	public void visit(EqualsTo equalsTo) {
		visitExpression(equalsTo, null, NoQueryConditionOperator.EQUALS);
	}

	@Override
	public void visit(GreaterThan greaterThan) {
		visitExpression(greaterThan, null, NoQueryConditionOperator.GREATER_THAN);
	}

	@Override
	public void visit(GreaterThanEquals greaterThanEquals) {
		visitExpression(greaterThanEquals, null, NoQueryConditionOperator.GREATER_THAN_EQUALS);
	}

	@Override
	public void visit(InExpression inExpression) {

	}

	@Override
	public void visit(IsNullExpression isNullExpression) {

	}

	@Override
	public void visit(LikeExpression likeExpression) {

	}

	@Override
	public void visit(MinorThan minorThan) {
		visitExpression(minorThan, null, NoQueryConditionOperator.LESS_THAN);
	}

	@Override
	public void visit(MinorThanEquals minorThanEquals) {
		visitExpression(minorThanEquals, null, NoQueryConditionOperator.LESS_THAN_EQUALS);
	}

	@Override
	public void visit(NotEqualsTo notEqualsTo) {
		visitExpression(notEqualsTo, null, NoQueryConditionOperator.NOT_EQUALS);
	}

	@Override
	public void visit(Column tableColumn) {
		if (isSelect) {
			columns.addColumn(tables.getTableName(tableColumn.getTable().getName()), tableColumn.getColumnName());
		} else {
			NoQueryCondition condition = new NoQueryCondition();
			condition.setTableName(tables.getTableName(tableColumn.getTable().getName()));
			condition.setColumnName(tableColumn.getColumnName());

			where.addCondition(condition);
		}
	}

	@Override
	public void visit(SubSelect subSelect) {

	}

	@Override
	public void visit(CaseExpression caseExpression) {

	}

	@Override
	public void visit(WhenClause whenClause) {

	}

	@Override
	public void visit(ExistsExpression existsExpression) {

	}

	@Override
	public void visit(AllComparisonExpression allComparisonExpression) {

	}

	@Override
	public void visit(AnyComparisonExpression anyComparisonExpression) {

	}

	@Override
	public void visit(Concat concat) {

	}

	@Override
	public void visit(Matches matches) {

	}

	@Override
	public void visit(BitwiseAnd bitwiseAnd) {

	}

	@Override
	public void visit(BitwiseOr bitwiseOr) {

	}

	@Override
	public void visit(BitwiseXor bitwiseXor) {

	}

	@Override
	public void visit(CastExpression cast) {

	}

	@Override
	public void visit(Modulo modulo) {

	}

	@Override
	public void visit(AnalyticExpression aexpr) {

	}

	@Override
	public void visit(WithinGroupExpression wgexpr) {

	}

	@Override
	public void visit(ExtractExpression eexpr) {

	}

	@Override
	public void visit(IntervalExpression iexpr) {

	}

	@Override
	public void visit(OracleHierarchicalExpression oexpr) {

	}

	@Override
	public void visit(RegExpMatchOperator rexpr) {

	}

	@Override
	public void visit(JsonExpression jsonExpr) {

	}

	@Override
	public void visit(RegExpMySQLOperator regExpMySQLOperator) {

	}

	@Override
	public void visit(UserVariable var) {

	}

	@Override
	public void visit(NumericBind bind) {

	}

	@Override
	public void visit(KeepExpression aexpr) {

	}

	@Override
	public void visit(Table tableName) {
		tables.addTable(tableName.getName(), tableName.getAlias() != null ? tableName.getAlias().getName() : tableName.getName());
	}

	@Override
	public void visit(SubJoin subjoin) {

	}

	@Override
	public void visit(LateralSubSelect lateralSubSelect) {

	}

	@Override
	public void visit(ValuesList valuesList) {

	}

	@Override
	public void visit(AllColumns allColumns) {
		columns.addColumn(Lists.newArrayList(tables.getTableNames()).get(0), "*");
	}

	@Override
	public void visit(AllTableColumns allTableColumns) {
		columns.addColumn(tables.getAlias(allTableColumns.getTable().getName()), "*");
	}

	@Override
	public void visit(SelectExpressionItem selectExpressionItem) {
		selectExpressionItem.getExpression().accept(this);
	}

}
