import {Args, Mutation, Query, Resolver} from "@nestjs/graphql";
import {IssueService} from "@/apis/issue/issue.service";
import {CreateResponse} from "@/apis/integration/dto/create.response";
import {UseGuards} from "@nestjs/common";
import {JwtAuthGuard} from "@/commons/auth/jwt-auth.guard";
import {CreateIssueRequest} from "@/apis/issue/dto/request/create-issue.request";
import {Signature} from "@/commons/decorator/signature";
import {IssueResponse} from "@/apis/issue/dto/response/issue.response";

@Resolver()
export class IssueResolver {
  constructor(
    private readonly issueService: IssueService
  ) {}

  @Query(() => IssueResponse)
  async getIssue() {
    const issue = await this.issueService.getIssueById('64681724bc738d40757b11f9')
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