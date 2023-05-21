import { IssueResponse } from './issue.response';
import {Field, Int, ObjectType} from "@nestjs/graphql";
import {Issue, User} from "@prisma/client";

@ObjectType()
export class IssuesResponse {
  @Field(() => [IssueResponse])
  issues: IssueResponse[];

  @Field(() => Int)
  countOfTotal: number;

  static fromIssues(issues: (Issue & { user: User })[], countOfTotal: number): IssuesResponse {
    const response = new IssuesResponse();
    response.issues = issues.map(issue => IssueResponse.fromIssue(issue));
    response.countOfTotal = countOfTotal;

    return response;
  }
}
