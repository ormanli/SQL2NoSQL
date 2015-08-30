package com.sql2nosql.visitor;

import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnalyticExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
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
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.ValuesList;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;

import com.sql2nosql.node.where.NoQueryCondition;
import com.sql2nosql.node.where.NoQueryConditionChain;
import com.sql2nosql.node.where.NoQueryConditionOperator;
import com.sql2nosql.node.where.NoQueryWhere;

public class SQLVisitor implements ExpressionVisitor, SelectVisitor,
		StatementVisitor, FromItemVisitor {

	private String table;
	private NoQueryWhere where = new NoQueryWhere();

	public String getTable() {
		return table;
	}

	public NoQueryWhere getWhere() {
		return where;
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

	}

	@Override
	public void visit(LongValue longValue) {
		NoQueryCondition condition = where.peekCondition();
		condition.setValue(longValue.getValue());
	}

	@Override
	public void visit(DateValue dateValue) {

	}

	@Override
	public void visit(TimeValue timeValue) {

	}

	@Override
	public void visit(TimestampValue timestampValue) {

	}

	@Override
	public void visit(Parenthesis parenthesis) {

	}

	@Override
	public void visit(StringValue stringValue) {

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

	@Override
	public void visit(AndExpression andExpression) {
		andExpression.getLeftExpression().accept(this);
		andExpression.getRightExpression().accept(this);

		NoQueryCondition condition = where.peekCondition();
		condition.setChain(NoQueryConditionChain.AND);
	}

	@Override
	public void visit(OrExpression orExpression) {
		orExpression.getLeftExpression().accept(this);
		orExpression.getRightExpression().accept(this);

		NoQueryCondition condition = where.peekCondition();
		condition.setChain(NoQueryConditionChain.OR);
	}

	@Override
	public void visit(Between between) {

	}

	@Override
	public void visit(EqualsTo equalsTo) {
		equalsTo.getLeftExpression().accept(this);
		equalsTo.getRightExpression().accept(this);

		NoQueryCondition condition = where.peekCondition();
		condition.setOperator(NoQueryConditionOperator.EQUALS);
	}

	@Override
	public void visit(GreaterThan greaterThan) {

	}

	@Override
	public void visit(GreaterThanEquals greaterThanEquals) {

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

	}

	@Override
	public void visit(MinorThanEquals minorThanEquals) {

	}

	@Override
	public void visit(NotEqualsTo notEqualsTo) {

	}

	@Override
	public void visit(Column tableColumn) {
		NoQueryCondition condition = new NoQueryCondition();
		condition.setColumnName(tableColumn.getColumnName());

		where.addCondition(condition);
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
		table = tableName.getName();
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

}
