import {Args, Mutation, Query, Resolver} from "@nestjs/graphql";
import {IssueService} from "@/apis/issue/issue.service";
import {CreateResponse} from "@/apis/integration/dto/create.response";
import {UseGuards} from "@nestjs/common";
import {JwtAuthGuard} from "@/commons/auth/jwt-auth.guard";
import {CreateIssueRequest} from "@/apis/issue/dto/request/create-issue.request";
import {Signature} from "@/commons/decorator/signature";
import {IssueResponse} from "@/apis/issue/dto/response/issue.response";
import {IssueRequest} from "@/apis/issue/dto/request/issue.request";
import {IssuesRequest} from "@/apis/issue/dto/request/issues.request";
import {IssuesResponse} from "@/apis/issue/dto/response/issues.response";

@Resolver()
export class IssueResolver {
  constructor(
    private readonly issueService: IssueService
  ) {}

  @Query(() => IssuesResponse)
  async getIssues(@Args('input') request: IssuesRequest) {
    const issues = await this.issueService.getIssues(request)
    const countOfIssues = await this.issueService.countIssues(request)
    return IssuesResponse.fromIssues(issues, countOfIssues)
  }

  @Query(() => IssueResponse)
  async getIssue(@Args('input') request: IssueRequest) {
    const issue = await this.issueService.getIssueById(request.id)
    return IssueResponse.fromIssue(issue)
  }

  @UseGuards(JwtAuthGuard)
  @Mutation(() => CreateResponse)
  async createIssue(@Args('input') request: CreateIssueRequest, @Signature() signature) {
    const fields = request.setUserid(signature.id)
    const issueId = await this.issueService.createIssue(fields)
    return CreateResponse.fromId(issueId)
  }
}