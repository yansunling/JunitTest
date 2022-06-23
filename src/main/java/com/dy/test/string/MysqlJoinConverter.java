package com.dy.test.string;
/*
 * Date: 11-12-1
 */

import gudusoft.gsqlparser.*;
import gudusoft.gsqlparser.nodes.*;
import gudusoft.gsqlparser.stmt.TSelectSqlStatement;

import java.util.ArrayList;

public class MysqlJoinConverter
{

	enum jointype {
		inner, left, right
	};

	class FromClause
	{

		TTable joinTable;
		String joinClause;
		String condition;
	}

	class JoinCondition
	{

		public String lefttable, righttable, leftcolumn, rightcolumn;
		public jointype jt;
		public Boolean used;
		public TExpression lexpr, rexpr, expr;
	}

	class getJoinConditionVisitor implements IExpressionVisitor
	{

		Boolean isFirstExpr = true;
		ArrayList<JoinCondition> jrs = new ArrayList<JoinCondition>( );

		public ArrayList<JoinCondition> getJrs( )
		{
			return jrs;
		}

		boolean is_compare_condition( int t )
		{
			return ( ( t == TExpression.simple_comparison_conditions )
					|| ( t == TExpression.group_comparison_conditions ) || ( t == TExpression.in_conditions ) );
		}

		private void analyzeJoinCondition( TExpression expr,
				TExpression parent_expr )
		{
			TExpression slexpr, srexpr, lc_expr = expr;

			if ( is_compare_condition( lc_expr.getExpressionType( ) ) )
			{
				slexpr = lc_expr.getLeftOperand( );
				srexpr = lc_expr.getRightOperand( );

				if ( slexpr.isOracleOuterJoin( ) || srexpr.isOracleOuterJoin( ) )
				{
					JoinCondition jr = new JoinCondition( );
					jr.used = false;
					jr.lexpr = slexpr;
					jr.rexpr = srexpr;
					jr.expr = expr;
					if ( slexpr.isOracleOuterJoin( ) )
					{
						//If the plus is on the left, the join type is right out join.
						jr.jt = jointype.right;
						// remove (+)
						slexpr.getEndToken( ).setString( "" );
					}
					if ( srexpr.isOracleOuterJoin( ) )
					{
						//If the plus is on the right, the join type is left out join.
						jr.jt = jointype.left;
						srexpr.getEndToken( ).setString( "" );
					}
					if ( ( slexpr.getExpressionType( ) == TExpression.simpleConstant ) )
					{
						jr.lefttable = null;
						jr.righttable = srexpr.getObjectOperand( )
								.getObjectString( );
					}
					else if ( srexpr.getExpressionType( ) == TExpression.simpleConstant )
					{
						jr.righttable = null;
						jr.lefttable = slexpr.getObjectOperand( )
								.getObjectString( );
					}
					else
					{
						jr.lefttable = slexpr.getObjectOperand( )
								.getObjectString( );
						jr.righttable = srexpr.getObjectOperand( )
								.getObjectString( );
					}
					jrs.add( jr );
					//System.out.printf( "join condition: %s\n", expr.toString( ) );
				}
				else if ( ( slexpr.getExpressionType( ) == TExpression.simpleObjectname )
						&& ( !slexpr.toString( ).startsWith( ":" ) )
						&& ( !slexpr.toString( ).startsWith( "?" ) )
						&& ( srexpr.getExpressionType( ) == TExpression.simpleObjectname )
						&& ( !srexpr.toString( ).startsWith( ":" ) )
						&& ( !srexpr.toString( ).startsWith( "?" ) ) )
				{
					JoinCondition jr = new JoinCondition( );
					jr.used = false;
					jr.lexpr = slexpr;
					jr.rexpr = srexpr;
					jr.expr = expr;
					jr.jt = jointype.inner;
					jr.lefttable = slexpr.getObjectOperand( ).getObjectString( );
					jr.righttable = srexpr.getObjectOperand( )
							.getObjectString( );
					jrs.add( jr );
					// System.out.printf(
					// "join condition: %s, %s:%d, %s:%d, %s\n",
					// expr.toString( ),
					// slexpr.toString( ),
					// slexpr.getExpressionType( ),
					// srexpr.toString( ),
					// srexpr.getExpressionType( ),
					// srexpr.getObjectOperand( ).getObjectType( ) );
				}
				else
				{
					// not a join condition
				}

			}

		}

		public boolean exprVisit( TParseTreeNode pNode, boolean isLeafNode )
		{
			TExpression expr = (TExpression) pNode;
			// System.out.printf("expr visited: %s\n",expr.toString());
			analyzeJoinCondition( expr, expr );
			return true;

		}

	}

	private String ErrorMessage = "";

	public String getErrorMessage( )
	{
		return ErrorMessage;
	}

	private int ErrorNo;

	private String query;

	public MysqlJoinConverter( String sql )
	{
		this.query = sql;
	}

	public MysqlJoinConverter() {
	}

	public String getQuery( )
	{
		// remove blank line from query
		return this.query.replaceAll( "(?m)^[ \t]*\r?\n", "" );
	}

	public int convert( )
	{

		TGSqlParser sqlparser = new TGSqlParser( EDbVendor.dbvoracle );
		sqlparser.sqltext = this.query;
		ErrorNo = sqlparser.parse( );
		if ( ErrorNo != 0 )
		{
			ErrorMessage = sqlparser.getErrormessage( );
			return ErrorNo;
		}
		if ( sqlparser.sqlstatements.get( 0 ).sqlstatementtype != ESqlStatementType.sstselect )
			return 0;
		TSelectSqlStatement select = (TSelectSqlStatement) sqlparser.sqlstatements.get( 0 );
		analyzeSelect( select );
		this.query = select.toString( );
		return ErrorNo;
	}

	private boolean isNameOfTable( TTable table, String name )
	{
		return ( name == null ) ? false : table.getName( )
				.equalsIgnoreCase( name );
	}

	private boolean isAliasOfTable( TTable table, String alias )
	{
		if ( table.getAliasClause( ) == null )
		{
			return false;
		}
		else
			return ( alias == null ) ? false : table.getAliasClause( )
					.toString( )
					.equalsIgnoreCase( alias );
	}

	private boolean isNameOrAliasOfTable( TTable table, String str )
	{
		return isAliasOfTable( table, str ) || isNameOfTable( table, str );
	}

	private boolean areTableJoined( TTable lefttable, TTable righttable,
			ArrayList<JoinCondition> jrs )
	{

		boolean ret = false;

		for ( int i = 0; i < jrs.size( ); i++ )
		{
			JoinCondition jc = jrs.get( i );
			if ( jc.used )
			{
				continue;
			}
			ret = isNameOrAliasOfTable( lefttable, jc.lefttable )
					&& isNameOrAliasOfTable( righttable, jc.righttable );
			if ( ret )
				break;
			ret = isNameOrAliasOfTable( lefttable, jc.righttable )
					&& isNameOrAliasOfTable( righttable, jc.lefttable );
			if ( ret )
				break;
		}

		return ret;
	}

	private String getJoinType( ArrayList<JoinCondition> jrs )
	{
		String str = "inner join";
		for ( int i = 0; i < jrs.size( ); i++ )
		{
			if ( jrs.get( i ).jt == jointype.left )
			{
				str = "left outer join";
				break;
			}
			else if ( jrs.get( i ).jt == jointype.right )
			{
				str = "right outer join";
				break;
			}
		}

		return str;
	}

	private ArrayList<JoinCondition> getJoinCondition( TTable lefttable,
			TTable righttable, ArrayList<JoinCondition> jrs )
	{
		ArrayList<JoinCondition> lcjrs = new ArrayList<JoinCondition>( );
		for ( int i = 0; i < jrs.size( ); i++ )
		{
			JoinCondition jc = jrs.get( i );
			if ( jc.used )
			{
				continue;
			}

			if ( isNameOrAliasOfTable( lefttable, jc.lefttable )
					&& isNameOrAliasOfTable( righttable, jc.righttable ) )
			{
				lcjrs.add( jc );
				jc.used = true;
			}
			else if ( isNameOrAliasOfTable( lefttable, jc.righttable )
					&& isNameOrAliasOfTable( righttable, jc.lefttable ) )
			{
				lcjrs.add( jc );
				jc.used = true;
			}
			else if ( ( jc.lefttable == null )
					&& ( isNameOrAliasOfTable( lefttable, jc.righttable ) || isNameOrAliasOfTable( righttable,
							jc.righttable ) ) )
			{
				// 'Y' = righttable.c1(+)
				lcjrs.add( jc );
				jc.used = true;
			}
			else if ( ( jc.righttable == null )
					&& ( isNameOrAliasOfTable( lefttable, jc.lefttable ) || isNameOrAliasOfTable( righttable,
							jc.lefttable ) ) )
			{
				// lefttable.c1(+) = 'Y'
				lcjrs.add( jc );
				jc.used = true;
			}
		}
		return lcjrs;
	}

	public void analyzeSelect( TSelectSqlStatement select )
	{
		if ( !select.isCombinedQuery( ) )
		{
			if ( select.tables.size( ) == 1 )
				return;

			if ( select.getWhereClause( ) == null )
			{
				if ( select.tables.size( ) > 1 )
				{
					// cross join
					String str = select.tables.getTable( 0 )
							.getAliasClause().toString();
					for ( int i = 1; i < select.tables.size( ); i++ )
					{
						str = str
								+ "\ncross join "
								+ select.tables.getTable( i )
										.getFullNameWithAliasString( );
					}

					for ( int k = select.joins.size( ) - 1; k > 0; k-- )
					{
						select.joins.removeJoin( k );
					}
					select.joins.getJoin( 0 ).setString( str );
				}
			}
			else
			{

				getJoinConditionVisitor v = new getJoinConditionVisitor( );

				// get join conditions
				select.getWhereClause( ).getCondition( ).preOrderTraverse( v );
				ArrayList<JoinCondition> jrs = v.getJrs( );

				// Console.WriteLine(jrs.Count);
				boolean tableUsed[] = new boolean[select.tables.size( )];
				for ( int i = 0; i < select.tables.size( ); i++ )
				{
					tableUsed[i] = false;
				}

				// make first table to be the left most joined table
				String fromclause = select.tables.getTable( 0 )
						.getAliasClause().toString();

				tableUsed[0] = true;
				boolean foundTableJoined;
				ArrayList<FromClause> fromClauses = new ArrayList<FromClause>( );
				for ( ;; )
				{
					foundTableJoined = false;

					for ( int i = 0; i < select.tables.size( ); i++ )
					{
						TTable lcTable1 = select.tables.getTable( i );

						TTable leftTable = null, rightTable = null;
						for ( int j = i + 1; j < select.tables.size( ); j++ )
						{
							TTable lcTable2 = select.tables.getTable( j );
							if ( areTableJoined( lcTable1, lcTable2, jrs ) )
							{
								if ( tableUsed[i] && ( !tableUsed[j] ) )
								{
									leftTable = lcTable1;
									rightTable = lcTable2;
								}
								else if ( ( !tableUsed[i] ) && tableUsed[j] )
								{
									leftTable = lcTable2;
									rightTable = lcTable1;
								}

								if ( ( leftTable != null )
										&& ( rightTable != null ) )
								{
									ArrayList<JoinCondition> lcjrs = getJoinCondition( leftTable,
											rightTable,
											jrs );
									FromClause fc = new FromClause( );
									fc.joinTable = rightTable;
									fc.joinClause = getJoinType( lcjrs );
									String condition = "";
									for ( int k = 0; k < lcjrs.size( ); k++ )
									{
										condition += lcjrs.get( k ).expr.toString( );
										if ( k != lcjrs.size( ) - 1 )
										{
											condition += " and ";
										}
										TExpression lc_expr = lcjrs.get( k ).expr;
										lc_expr.remove( );
									}
									fc.condition = condition;

									fromClauses.add( fc );
									tableUsed[i] = true;
									tableUsed[j] = true;

									foundTableJoined = true;
								}
							}
						}
					}

					if ( !foundTableJoined )
					{
						break;
					}
				}

				// are all join conditions used?
				for ( int i = 0; i < jrs.size( ); i++ )
				{
					JoinCondition jc = jrs.get( i );
					if ( !jc.used )
					{
						for ( int j = fromClauses.size( ) - 1; j >= 0; j-- )
						{
							if ( isNameOrAliasOfTable( fromClauses.get( j ).joinTable,
									jc.lefttable )
									|| isNameOrAliasOfTable( fromClauses.get( j ).joinTable,
											jc.righttable ) )
							{
								fromClauses.get( j ).condition += " and "
										+ jc.expr.toString( );
								jc.used = true;
								jc.expr.remove( );
								break;
							}
						}
					}
				}

				for ( int i = 0; i < select.tables.size( ); i++ )
				{
					if ( !tableUsed[i] )
					{
						ErrorNo++;
						ErrorMessage += String.format( "%sError %d, Message: %s",
								System.getProperty( "line.separator" ),
								ErrorNo,
								"This table has no join condition: "
										+ select.tables.getTable( i )
												.getFullName( ) );
					}
				}

				// link all join clause
				for ( int i = 0; i < fromClauses.size( ); i++ )
				{
					FromClause fc = fromClauses.get( i );
					// fromclause += System.getProperty("line.separator") +
					// fc.joinClause
					// +" "+fc.joinTable.getFullNameWithAliasString()+" on "+fc.condition;
					fromclause += "\n"
							+ fc.joinClause
							+ " "
							+ fc.joinTable.getAliasClause().toString()
							+ " on "
							+ fc.condition;
				}

				for ( int k = select.joins.size( ) - 1; k > 0; k-- )
				{
					select.joins.removeJoin( k );
				}

				select.joins.getJoin( 0 ).setString( fromclause );

				if ( ( select.getWhereClause( ).getCondition( ).getStartToken( ) == null )
						|| ( select.getWhereClause( )
								.getCondition( )
								.toString( )
								.trim( )
								.length( ) == 0 ) )
				{
					// no where condition, remove WHERE keyword
					select.getWhereClause( ).setString( " " );

				}
			}
		}
		else
		{
			analyzeSelect( select.getLeftStmt( ) );
			analyzeSelect( select.getRightStmt( ) );
		}
	}

	public static void main( String args[] )
	{
		// String sqltext = "SELECT e.employee_id,\n" +
		// "       e.last_name,\n" +
		// "       e.department_id\n" +
		// "FROM   employees e,\n" +
		// "       departments d\n" ;

		String sqltext = "SELECT e.employee_id,\n"
				+ "       e.last_name,\n"
				+ "       e.department_id\n"
				+ "FROM   employees e,\n"
				+ "       departments d\n"
				+ "WHERE  e.department_id = d.department_id";

		sqltext = "SELECT m.*, \n"
				+ "       altname.last_name  last_name_student, \n"
				+ "       altname.first_name first_name_student, \n"
				+ "       ccu.date_joined, \n"
				+ "       ccu.last_login, \n"
				+ "       ccu.photo_id, \n"
				+ "       ccu.last_updated \n"
				+ "FROM   summit.mstr m, \n"
				+ "       summit.alt_name altname, \n"
				+ "       smmtccon.ccn_user ccu \n"
				+ "WHERE  m.id =?\n"
				+ "       AND m.id = altname.id(+) \n"
				+ "       AND m.id = ccu.id(+) \n"
				+ "       AND altname.grad_name_ind(+) = '*'";

		// sqltext = "SELECT * \n" +
		// "FROM   summit.mstr m, \n" +
		// "       summit.alt_name altname, \n" +
		// "       smmtccon.ccn_user ccu \n" +
		// //"       uhelp.deg_coll deg \n" +
		// "WHERE  m.id = ? \n" +
		// "       AND m.id = altname.id(+) \n" +
		// "       AND m.id = ccu.id(+) \n" +
		// "       AND 'N' = ccu.admin(+) \n" +
		// "       AND altname.grad_name_ind(+) = '*'";

		// sqltext = "SELECT ppp.project_name proj_name, \n" +
		// "       pr.role_title    user_role \n" +
		// "FROM   jboss_admin.portal_application_location pal, \n" +
		// "       jboss_admin.portal_application pa, \n" +
		// "       jboss_admin.portal_user_app_location_role pualr, \n" +
		// "       jboss_admin.portal_location pl, \n" +
		// "       jboss_admin.portal_role pr, \n" +
		// "       jboss_admin.portal_pep_project ppp, \n" +
		// "       jboss_admin.portal_user pu \n" +
		// "WHERE  (pal.application_location_id = pualr.application_location_id \n"
		// +
		// "         AND pu.jbp_uid = pualr.jbp_uid \n" +
		// "         AND pu.username = 'USERID') \n" +
		// "       AND pal.uidr_uid = pl.uidr_uid \n" +
		// "       AND pal.application_id = pa.application_id \n" +
		// "       AND pal.application_id = pr.application_id \n" +
		// "       AND pualr.role_id = pr.role_id \n" +
		// "       AND pualr.project_id = ppp.project_id \n" +
		// "       AND pa.application_id = 'APPID' ";

		sqltext = "SELECT * \n"
				+ "FROM   smmtccon.ccn_menu menu, \n"
				+ "       smmtccon.ccn_page paget \n"
				+ "WHERE  ( menu.page_id = paget.page_id(+) ) \n"
				+ "       AND ( NOT enabled = 'N' ) \n"
				+ "       AND ( ( :parent_menu_id IS NULL \n"
				+ "               AND menu.parent_menu_id IS NULL ) \n"
				+ "              OR ( menu.parent_menu_id = :parent_menu_id ) ) \n"
				+ "ORDER  BY item_seq;";

		sqltext = "select *\n"
				+ "from  ods_trf_pnb_stuf_lijst_adrsrt2 lst\n"
				+ "		, ods_stg_pnb_stuf_pers_adr pas\n"
				+ "		, ods_stg_pnb_stuf_pers_nat nat\n"
				+ "		, ods_stg_pnb_stuf_adr adr\n"
				+ "		, ods_stg_pnb_stuf_np prs\n"
				+ "where \n"
				+ "		pas.soort_adres = lst.soort_adres\n"
				+ "	and prs.id = nat.prs_id(+)\n"
				+ "	and adr.id = pas.adr_id\n"
				+ "	and prs.id = pas.prs_id\n"
				+ "  and lst.persoonssoort = 'PERSOON'\n"
				+ "  and pas.einddatumrelatie is null  ";

		sqltext = "select *\n"
				+ "		from  ods_trf_pnb_stuf_lijst_adrsrt2 lst\n"
				+ "				, ods_stg_pnb_stuf_np prs\n"
				+ "				, ods_stg_pnb_stuf_pers_adr pas\n"
				+ "				, ods_stg_pnb_stuf_pers_nat nat\n"
				+ "		 		, ods_stg_pnb_stuf_adr adr\n"
				+ "		 where \n"
				+ "				pas.soort_adres = lst.soort_adres\n"
				+ "			and prs.id(+) = nat.prs_id\n"
				+ "			and adr.id = pas.adr_id\n"
				+ "			and prs.id = pas.prs_id\n"
				+ "		 and lst.persoonssoort = 'PERSOON'\n"
				+ "		  and pas.einddatumrelatie is null";

//		sqltext = "SELECT ppp.project_name proj_name, \n"
//				+ "       pr.role_title    user_role \n"
//				+ "FROM   jboss_admin.portal_application_location pal, \n"
//				+ "       jboss_admin.portal_application pa, \n"
//				+ "       jboss_admin.portal_user_app_location_role pualr, \n"
//				+ "       jboss_admin.portal_location pl, \n"
//				+ "       jboss_admin.portal_role pr, \n"
//				+ "       jboss_admin.portal_pep_project ppp, \n"
//				+ "       jboss_admin.portal_user pu \n"
//				+ "WHERE  (pal.application_location_id = pualr.application_location_id \n"
//				+ "         AND pu.jbp_uid = pualr.jbp_uid \n"
//				+ "         AND pu.username = 'USERID') \n"
//				+ "       AND pal.uidr_uid = pl.uidr_uid \n"
//				+ "       AND pal.application_id = pa.application_id \n"
//				+ "       AND pal.application_id = pr.application_id \n"
//				+ "       AND pualr.role_id = pr.role_id \n"
//				+ "       AND pualr.project_id = ppp.project_id \n"
//				+ "       AND pa.application_id = 'APPID'";
//
//		sqltext = "select *\n"
//				+ "from  ods_trf_pnb_stuf_lijst_adrsrt2 lst\n"
//				+ "		, ods_stg_pnb_stuf_np prs\n"
//				+ "		, ods_stg_pnb_stuf_pers_adr pas\n"
//				+ "		, ods_stg_pnb_stuf_pers_nat nat\n"
//				+ "		, ods_stg_pnb_stuf_adr adr\n"
//				+ "where \n"
//				+ "		pas.soort_adres = lst.soort_adres\n"
//				+ "	and prs.id = nat.prs_id(+)\n"
//				+ "	and adr.id = pas.adr_id\n"
//				+ "	and prs.id = pas.prs_id\n"
//				+ "  and lst.persoonssoort = 'PERSOON'\n"
//				+ "   and pas.einddatumrelatie is null";

//		sqltext = "select *\n"
//			+ "from  ods_trf_pnb_stuf_lijst_adrsrt2 lst,\n"
//			+ "       ods_stg_pnb_stuf_np prs,\n"
//			+ "       ods_stg_pnb_stuf_pers_adr pas,\n"
//			+ "       ods_stg_pnb_stuf_pers_nat nat,\n"
//			+ "       ods_stg_pnb_stuf_adr adr\n"
//			+ "where  pas.soort_adres = lst.soort_adres\n"
//			+ "       and prs.id(+) = nat.prs_id\n"
//			+ "       and adr.id = pas.adr_id\n"
//			+ "       and prs.id = pas.prs_id\n"
//			+ "       and lst.persoonssoort = 'PERSOON'\n"
//			+ "       and pas.einddatumrelatie is null\n";

        sqltext = "SELECT e.employee_id,\n" +
             "       e.last_name,\n" +
             "       e.department_id\n" +
             "FROM   employees e,\n" +
             "       departments d\n" +
             "WHERE  e.department_id = d.department_id(+)";

        sqltext = "SELECT e.employee_id,\n" +
             "       e.last_name,\n" +
             "       e.department_id\n" +
             "FROM   employees e,\n" +
             "       departments d\n" +
             "WHERE  e.department_id(+) = d.department_id";

		System.out.println( "SQL with Oracle propriety joins" );
		System.out.println( sqltext );


		sqltext = "select \n" +
				"main.create_user_id AS create_user_id ,  -- 申请人 \n" +
				"main.claims_id AS claims_id ,  -- 理赔编号 \n" +
				"main.claims_info AS claims_info ,  -- 理赔类型 \n" +
				"main.claims_status AS claims_status ,  -- 理赔单状态 \n" +
				"main.order_id AS order_id ,  -- 运单号 \n" +
				"main.delivery_no AS delivery_no ,  -- 次运号 \n" +
				"main.goods_count AS goods_count ,  -- 件数 \n" +
				"main.claims_count AS claims_count ,  -- 理赔件数 \n" +
				"main.ticket_org_id AS ticket_org_id ,  -- 开单部门 \n" +
				"main.ticket_time AS ticket_time ,  -- 开单日期 \n" +
				"hand.send_org_id AS send_org_id ,  -- 装车部门 \n" +
				"date_format(hand.load_date, '%Y-%m-%d %H:%i:%s') AS load_date ,  -- 装车日期 \n" +
				"hand.arr_org_id AS arr_org_id ,  -- 到达部门 \n" +
				"date_format(hand.arrive_time, '%Y-%m-%d %H:%i:%s') AS arrive_time ,  -- 到达日期 \n" +
				"main.transfer_city AS transfer_city ,  -- 二次中转城市 \n" +
				"trans.trans_doc_type AS trans_doc_type ,  -- 运输方式 \n" +
				"main.claims_customer_type AS claims_customer_type ,  -- 索赔客户类型 \n" +
				"main.customer_name AS customer_name ,  -- 索赔客户名称 \n" +
				"main.customer_id AS customer_id ,  -- 索赔客户编码 \n" +
				"main.send_customer_id AS send_customer_id ,  -- 发件客户id \n" +
				"main.customer_contact_way AS customer_contact_way ,  -- 索赔客户联系方式 \n" +
				"main.income_name AS income_name ,  -- 收款人 \n" +
				"main.income_contact_way AS income_contact_way ,  -- 收款人联系方式 \n" +
				"main.certificate_type AS certificate_type ,  -- 证件类型 \n" +
				"main.certificate_no AS certificate_no ,  -- 证件号 \n" +
				"main.bank_name AS bank_name ,  -- 收款人开户行 \n" +
				"main.card_id AS card_id ,  -- 收款人银行账号 \n" +
				"main.order_pay_type AS order_pay_type ,  -- 付款方式 \n" +
				"main.order_close_type AS order_close_type ,  -- 签收类型 \n" +
				"main.insure_value AS insure_value ,  -- 保价声明价值 \n" +
				"main.damage_amt AS damage_amt ,  -- 货损金额 \n" +
				"main.apply_amt AS apply_amt ,  -- 申请理赔金额 \n" +
				"main.judge_amt AS judge_amt ,  -- 保价理赔金额 \n" +
				"main.extra_amt AS extra_amt ,  -- 多赔金额 \n" +
				"main.apply_time AS apply_time ,  -- 申请日期 \n" +
				"main.apply_org_id AS apply_org_id ,  -- 申请部门 \n" +
				"main.apply_user_id AS apply_user_id ,  -- 申请人工号 \n" +
				"main.apply_big_area AS apply_big_area ,  -- 申请大区 \n" +
				"main.claims_reason AS claims_reason ,  -- 理赔类型描述 \n" +
				"main.claims_bg_type AS claims_bg_type ,  -- 理赔大类 \n" +
				"main.claims_sm_type AS claims_sm_type ,  -- 理赔小类 \n" +
				"main.rejecte_remark AS rejecte_remark -- 退回原因 \n" +
				"from (\n" +
				"select \n" +
				"main.create_user_id AS create_user_id ,  \n" +
				"main.claims_id AS claims_id ,  \n" +
				"main.claims_info AS claims_info ,  \n" +
				"main.claims_status AS claims_status ,  \n" +
				"main.order_id AS order_id ,  \n" +
				"profile.delivery_no AS delivery_no ,  \n" +
				"profile.goods_count AS goods_count ,  \n" +
				" sum(detail.claims_count) AS claims_count ,  \n" +
				"profile.ticket_org_id AS ticket_org_id ,  \n" +
				"date_format(profile.ticket_time, '%Y-%m-%d %H:%i:%s') AS ticket_time ,  \n" +
				"profile.transfer_city AS transfer_city ,    \n" +
				"main.claims_customer_type AS claims_customer_type ,  \n" +
				"main.customer_name AS customer_name ,  \n" +
				"main.customer_id AS customer_id ,  \n" +
				"profile.send_customer_id AS send_customer_id ,  \n" +
				"main.customer_contact_way AS customer_contact_way ,  \n" +
				"main.income_name AS income_name ,  \n" +
				"main.income_contact_way AS income_contact_way ,  \n" +
				"main.certificate_type AS certificate_type ,  \n" +
				"main.certificate_no AS certificate_no ,  \n" +
				"main.bank_name AS bank_name ,  \n" +
				"main.card_id AS card_id ,  \n" +
				"profile.order_pay_type AS order_pay_type ,  \n" +
				"profile.order_close_type AS order_close_type ,  \n" +
				"profile.insure_value AS insure_value ,  \n" +
				"round(main.damage_amt / 100, 2) AS damage_amt ,  \n" +
				"round(main.apply_amt / 100, 2) AS apply_amt ,  \n" +
				"round(main.judge_amt / 100, 2) AS judge_amt ,  \n" +
				"round(main.extra_amt / 100, 2) AS extra_amt ,  \n" +
				"date_format(main.apply_time, '%Y-%m-%d %H:%i:%s') AS apply_time ,  \n" +
				"main.apply_org_id AS apply_org_id ,  \n" +
				"main.apply_user_id AS apply_user_id ,  \n" +
				"main.apply_big_area AS apply_big_area ,  \n" +
				"main.claims_reason AS claims_reason ,  \n" +
				"main.claims_bg_type AS claims_bg_type ,  \n" +
				"main.claims_sm_type AS claims_sm_type ,  \n" +
				"main.rejecte_remark AS rejecte_remark ,\n" +
				"main.create_time as create_time\n" +
				"from tmsp.tmsp_claims_profile  main  \n" +
				"left join tmsp.tmsp_claims_detail detail on detail.claims_id=main.claims_id \n" +
				"left join tmsp.tmsp_order_profile profile on profile.order_id = main.order_id \n" +
				"group by main.claims_id \n" +
				")  main  \n" +
				"left join tmsp.tmsp_stock_io_items stock on stock.order_id = main.order_id \n" +
				"left join tmsp.tmsp_hand_doc hand on hand.doc_id = stock.doc_id \n" +
				"left join tmsp.tmsp_hand_trans_doc trans on trans.trans_doc_id = stock.trans_doc_id \n" +
				" where main.order_id = ? \n" +
				" and main.customer_name = ? \n" +
				" and main.create_time >= ? \n" +
				" and main.create_time <= ? \n" +
				" and main.claims_id = ? \n" +
				" and main.claims_status = ? \n" +
				" and hand.send_org_id = ? \n" +
				" and hand.arr_org_id = ? \n" +
				" and main.apply_user_id = ? \n" +
				" group by main.claims_id \n" +
				"order by main.create_time desc \n";



		MysqlJoinConverter converter = new MysqlJoinConverter( sqltext );
		if ( converter.convert( ) != 0 )
		{

			System.out.println( converter.getErrorMessage( ) );
		}
		else
		{
			System.out.println( "\nSQL in ANSI joins" );
			System.out.println( converter.getQuery( ) );




		}
	}
}